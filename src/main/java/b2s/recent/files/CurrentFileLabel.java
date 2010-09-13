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

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;

public class CurrentFileLabel extends JLabel implements ListSelectionListener {
    private static final String PATH_SEPERATOR = File.separator;
    private static final int MAX_NUMBER_OF_PARENT_DIRECTORIES = 4;
    private DataObjectUtil dataObjectUtil = new DataObjectUtil();

    public CurrentFileLabel() {
        Font currentFont = getFont();
        setFont(new Font(
            currentFont.getName(),
            Font.PLAIN,
            currentFont.getSize() - 2
        ));
        setBorder(new TopBorder());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        JList fileList = (JList) e.getSource();

        int selectedFile = fileList.getSelectedIndex();
        DataObject file = (DataObject) fileList.getModel().getElementAt(selectedFile);
        FileObject fileObject = dataObjectUtil.fileFor(file);

        String absolutePath = fileObject.getPath();

        String pathWithNoFile = "";
        if (absolutePath.contains(PATH_SEPERATOR)) {
            pathWithNoFile = absolutePath.substring(0, absolutePath.lastIndexOf(PATH_SEPERATOR));
        }

        boolean hasTooManyParentDirectories = false;
        List<String> parentDirectories = parentDirsOf(pathWithNoFile);
        if (parentDirectories.size() >= MAX_NUMBER_OF_PARENT_DIRECTORIES) {
            hasTooManyParentDirectories = true;
            StringBuilder builder = new StringBuilder();
            for (int i=parentDirectories.size() - MAX_NUMBER_OF_PARENT_DIRECTORIES;i<parentDirectories.size();i++) {
                builder.append(PATH_SEPERATOR).append(parentDirectories.get(i));
            }
            pathWithNoFile = builder.toString();
        }

        if (!pathWithNoFile.startsWith(PATH_SEPERATOR) && isNotEmpty(pathWithNoFile)) {
            pathWithNoFile = PATH_SEPERATOR + pathWithNoFile;
        }

        if (hasTooManyParentDirectories) {
            pathWithNoFile = "..." + pathWithNoFile;
        }

        setText(pathWithNoFile);
    }

    private boolean isNotEmpty(String pathWithNoFile) {
        return pathWithNoFile.trim().length() > 0;
    }

    private List<String> parentDirsOf(String path) {
        List<String> dirNames = new ArrayList<String>();
        for (int i=path.indexOf(PATH_SEPERATOR);i>-1;i=path.indexOf(PATH_SEPERATOR, i+1)) {
            int secondSlash = path.indexOf(PATH_SEPERATOR, i+1);
            if (secondSlash > -1) {
                dirNames.add(path.substring(i+1, secondSlash));
            } else {
                dirNames.add(path.substring(i+1));
            }
        }
        return dirNames;
    }

    void setDataObjectUtil(DataObjectUtil dataObjectUtil) {
        this.dataObjectUtil = dataObjectUtil;
    }

}
