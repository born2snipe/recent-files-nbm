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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class RecentFilesAction implements ActionListener {
    private RecentFileDialogDisplayer recentFileDialogDisplayer;

    public RecentFilesAction() {
        recentFileDialogDisplayer = new RecentFileDialogDisplayer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        recentFileDialogDisplayer.displayRecentFiles();
    }

    void setRecentFileDialogDisplayer(RecentFileDialogDisplayer dialogDisplayer) {
        this.recentFileDialogDisplayer = dialogDisplayer;
    }
}
