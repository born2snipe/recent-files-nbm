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

import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

import javax.swing.*;
import java.awt.*;

public class RecentFileDialogDisplayer {
    void displayRecentFiles(final RecentFiles recentFiles) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final JDialog dialog = new JDialog(
                        WindowManager.getDefault().getMainWindow(),
                        NbBundle.getMessage(RecentFileDialogDisplayer.class, "CTL_RecentFilesAction"),
                        true
                );

                CurrentFileLabel currentFileLabel = new CurrentFileLabel();
                ListModel model = new DataObjectListModel(recentFiles.asList());
                JList list = new JList(model);
                list.addListSelectionListener(currentFileLabel);
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list.setCellRenderer(new DataObjectCellRenderer());
                list.setCursor(new Cursor(Cursor.HAND_CURSOR));
                list.addKeyListener(new OpenFileListener(dialog));
                list.addKeyListener(new CloseDialogListener(dialog));
                list.addKeyListener(new WrappedScrollingListener(list));
                list.addMouseListener(new OpenFileListener(dialog));
                list.addMouseMotionListener(new SelectRowOnMouseHoverListener());
                list.setSelectedIndex(0);

                dialog.add(list, BorderLayout.CENTER);
                dialog.add(currentFileLabel, BorderLayout.SOUTH);
                dialog.setMinimumSize(new Dimension(200, 0));

                dialog.pack();
                dialog.setLocationRelativeTo(WindowManager.getDefault().getMainWindow());
                dialog.setResizable(false);
                dialog.setVisible(true);
            }
        });
    }
}
