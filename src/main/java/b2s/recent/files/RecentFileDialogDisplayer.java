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

import java.awt.Cursor;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

public class RecentFileDialogDisplayer {
    private FileOpener fileOpener = new FileOpener();

    void displayRecentFiles(List<DataObject> dataObjects) {
        final JDialog dialog = new JDialog(
                WindowManager.getDefault().getMainWindow(),
                NbBundle.getMessage(RecentFileDialogDisplayer.class, "CTL_RecentFilesAction"),
                true
        );
        
        ListModel model = new DataObjectListModel(dataObjects);
        JList list = new JList(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new DataObjectCellRenderer());
        list.setCursor(new Cursor(Cursor.HAND_CURSOR));
        list.addKeyListener(new OpenFileListener(dialog));
        list.addKeyListener(new CloseDialogListener(dialog));
        list.addMouseListener(new OpenFileListener(dialog));
        list.addMouseMotionListener(new SelectRowOnMouseHoverListener());
        list.setSelectedIndex(0);

        dialog.add(list);
        dialog.pack();
        dialog.setLocationRelativeTo(WindowManager.getDefault().getMainWindow());
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
}
