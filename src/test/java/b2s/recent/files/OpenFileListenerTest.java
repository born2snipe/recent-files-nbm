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

import java.awt.event.MouseEvent;
import org.openide.loaders.DataObject;
import javax.swing.ListModel;
import org.junit.Before;
import java.awt.Dialog;
import java.awt.event.KeyEvent;
import javax.swing.JList;
import org.junit.Test;
import static org.mockito.Mockito.*;


public class OpenFileListenerTest {
    private JList list;
    private Dialog dialog;
    private OpenFileListener listener;
    private ListModel model;
    private DataObject dataObject;
    private FileOpener fileOpener;

    @Test
    public void mouseClicked_not_LeftMouseButton() {
        listener.mouseClicked(mouse(MouseEvent.BUTTON2));

        verifyZeroInteractions(dialog, fileOpener);
    }

    @Test
    public void mouseClicked_LeftMouseButton() {
        listener.mouseClicked(mouse(MouseEvent.BUTTON1));

        verify(dialog).dispose();
        verify(fileOpener).open(dataObject);
    }

    @Test
    public void keyReleased_not_ENTER() {
        listener.keyReleased(key(KeyEvent.VK_0));

        verifyZeroInteractions(dialog, fileOpener);
    }


    @Test
    public void keyReleased_ENTER() {
        listener.keyReleased(key(KeyEvent.VK_ENTER));

        verify(dialog).dispose();
        verify(fileOpener).open(dataObject);
    }

    @Before
    public void setUp() {
        list = mock(JList.class);
        dialog = mock(Dialog.class);
        model = mock(ListModel.class);
        dataObject = mock(DataObject.class);
        fileOpener = mock(FileOpener.class);

        listener = new OpenFileListener(dialog);
        listener.setFileOpener(fileOpener);

        when(list.getModel()).thenReturn(model);
        when(list.getSelectedIndex()).thenReturn(1);
        when(model.getElementAt(1)).thenReturn(dataObject);
    }

    private KeyEvent key(int keyCode) {
        return new KeyEvent(list, 0, 0L, 0, keyCode, 'x');
    }

    private MouseEvent mouse(int buttonCode) {
        MouseEvent event = mock(MouseEvent.class);
        when(event.getSource()).thenReturn(list);
        when(event.getButton()).thenReturn(buttonCode);
        return event;
    }
}
