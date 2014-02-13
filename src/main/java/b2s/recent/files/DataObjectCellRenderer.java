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

import b2s.recent.files.startup.RecentFileListInstaller;
import java.awt.Color;
import java.awt.Component;
import java.beans.BeanInfo;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;

public class DataObjectCellRenderer extends DefaultListCellRenderer {
    private static final Color FILE_ALREADY_OPEN = new Color(191,255,194);
    private EditorUtil editorUtil = new EditorUtil();
    private ProjectUtil projectUtil = new ProjectUtil();
    private RecentFiles recentFiles = RecentFileListInstaller.recentFiles;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        DataObject dataObject = (DataObject) value;
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
        label.setToolTipText(fileObject.getPath());

        if (!isSelected && editorUtil.hasEditorAlready(dataObject)) {
            label.setBackground(FILE_ALREADY_OPEN);
            label.setForeground(Color.BLACK);
        }

        return label;
    }

    private String filename(FileObject fileObject) {
        String path = fileObject.getPath();
        return path.substring(path.lastIndexOf("/")+1);
    }

}
