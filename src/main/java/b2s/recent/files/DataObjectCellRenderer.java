/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package b2s.recent.files;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.beans.BeanInfo;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.units.JustNow;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;

public class DataObjectCellRenderer implements ListCellRenderer<TimeStampedDataObject>{
    private static final Color FILE_ALREADY_OPEN = new Color(191,255,194);
    private EditorUtil editorUtil = new EditorUtil();
    private ProjectUtil projectUtil = new ProjectUtil();
    private RecentFiles recentFiles = RecentFileListInstaller.recentFiles;
    private PrettyTime prettyTime;
    //TODO allow to toggle this within an option dialog
    private boolean showRelativeTime = true;

    public DataObjectCellRenderer() {
        prettyTime = new PrettyTime();
        prettyTime.setReference(new Date());
        // remove the time description "moments ago" for durations less than 5 minutes
        prettyTime.removeUnit(JustNow.class);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends TimeStampedDataObject> list, TimeStampedDataObject value, int index, boolean isSelected, boolean cellHasFocus) {

        final TimeStampedDataObject timeStampedDataObject = (TimeStampedDataObject) value;
        final JLabel label = new JLabel();
        final JLabel timeLabel = new JLabel(" " + prettyTime.format(timeStampedDataObject.getTimestamp()) + "");

        DataObject dataObject = (DataObject) timeStampedDataObject.getDataObject();
        label.setIcon(new ImageIcon(dataObject.getNodeDelegate().getIcon(BeanInfo.ICON_COLOR_16x16)));

        FileObject fileObject = dataObject.getPrimaryFile();

        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append(filename(fileObject));
        if (recentFiles.filesWithTheSameName(dataObject.getName()) > 1) {
            String color = "4572DF";
            if (isSelected) {
                color = "FFFFFF";
            }
            builder.append(" <b><font color='#").append(color).append("'>[");
            builder.append(projectUtil.projectNameFor(fileObject)).append("]</font></b>");
            }
        builder.append("</html>");

        label.setText(builder.toString());

        JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new BorderLayout());
        layoutPanel.setToolTipText(fileObject.getPath());

        if (cellHasFocus) {
            layoutPanel.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
            timeLabel.setForeground(list.getSelectionForeground());
            label.setBackground(list.getSelectionBackground());
            timeLabel.setBackground(list.getSelectionBackground());
        } else {
            layoutPanel.setBackground(list.getBackground());
            label.setForeground(list.getForeground());
            timeLabel.setForeground(list.getForeground());
            label.setBackground(list.getBackground());
            timeLabel.setBackground(list.getBackground());
        }
        if (!isSelected && editorUtil.hasEditorAlready(dataObject)) {
            label.setBackground(FILE_ALREADY_OPEN);
        }

        label.setVerticalTextPosition(SwingConstants.TOP);
        timeLabel.setVerticalTextPosition(SwingConstants.TOP);
        timeLabel.setIcon(new EmptyIcon(label.getIcon().getIconWidth(), label.getIcon().getIconHeight()));
        //add left-aligned label for filename and right-aligned label for timestamp
        layoutPanel.add(label, BorderLayout.CENTER);
        if (showRelativeTime) {
            layoutPanel.add(timeLabel, BorderLayout.EAST);
        }
        return layoutPanel;
    }

    private String filename(FileObject fileObject) {
        String path = fileObject.getPath();
        return path.substring(path.lastIndexOf("/")+1);
    }

    private static class EmptyIcon implements Icon {

        private int height;
        private int width;

        public EmptyIcon(int width, int height) {
            this.height = height;
            this.width = width;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
        }

            @Override
            public int getIconWidth() {
                return width;
            }

            @Override
            public int getIconHeight() {
                return height;
            }
    }

}
