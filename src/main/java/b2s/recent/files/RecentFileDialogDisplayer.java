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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JDialog;
import javax.swing.JList;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

public class RecentFileDialogDisplayer {
    void displayRecentFiles() {
        final JDialog dialog = new JDialog(
                WindowManager.getDefault().getMainWindow(),
                NbBundle.getMessage(RecentFileDialogDisplayer.class, "CTL_RecentFilesAction"),
                true
        );
        final JList list = new JList(new DataObjectListModel(RecentFileListInstaller.dataObjects));
        list.setCellRenderer(new DataObjectCellRenderer());
        list.setCursor(new Cursor(Cursor.HAND_CURSOR));
        list.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        dialog.dispose();
                        break;
                }
            }
        });
        list.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        list.setSelectedIndex(0);

        dialog.add(list);
        dialog.pack();
        dialog.setLocationRelativeTo(WindowManager.getDefault().getMainWindow());
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
}
