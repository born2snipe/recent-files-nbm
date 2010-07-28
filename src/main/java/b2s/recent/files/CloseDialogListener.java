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

import java.awt.Dialog;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CloseDialogListener extends KeyAdapter {
    private final Dialog dialog;

    public CloseDialogListener(Dialog dialog) {
        this.dialog = dialog;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dialog.dispose();
        }
    }
}
