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

import java.awt.Color;
import java.awt.Component;
import java.beans.BeanInfo;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;

public class DataObjectCellRenderer extends DefaultListCellRenderer {
    private EditorUtil editorUtil = new EditorUtil();
    private List<DataObject> openedFiles;

    public DataObjectCellRenderer() {
        openedFiles = editorUtil.currentFilesOpened();
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        DataObject dataObject = (DataObject) value;
        label.setIcon(new ImageIcon(dataObject.getNodeDelegate().getIcon(BeanInfo.ICON_COLOR_16x16)));
        
        FileObject fileObject = dataObject.getPrimaryFile();
        label.setText(filename(fileObject));
        label.setToolTipText(fileObject.getPath());

        if (!isSelected && openedFiles.contains(dataObject)) {
            label.setBackground(new Color(191,255,194));
        }

        return label;
    }

    private String filename(FileObject fileObject) {
        String path = fileObject.getPath();
        return path.substring(path.lastIndexOf("/")+1);
    }

}
