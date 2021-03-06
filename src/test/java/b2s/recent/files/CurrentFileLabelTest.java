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

import org.junit.Before;
import javax.swing.ListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CurrentFileLabelTest {
    private DataObjectUtil dataObjectUtil;
    private DataObject dataObject;
    private FileObject fileObject;
    private JList list;
    private ListModel model;
    private CurrentFileLabel label;
    private TimeStampedDataObject timeStampedDataObject;

    @Before
    public void setUp() {
        dataObjectUtil = mock(DataObjectUtil.class);
        dataObject = mock(DataObject.class);
        fileObject = mock(FileObject.class);
        list = mock(JList.class);
        model = mock(ListModel.class);
        timeStampedDataObject = new TimeStampedDataObject(dataObject);

        label = new CurrentFileLabel();
        label.setDataObjectUtil(dataObjectUtil);

        when(list.getSelectedIndex()).thenReturn(0);
        when(list.getModel()).thenReturn(model);
        when(model.getElementAt(0)).thenReturn(timeStampedDataObject);
        when(dataObjectUtil.fileFor(dataObject)).thenReturn(fileObject);
    }

    @Test
    public void showAtMostFourParentDirectories() {
        when(fileObject.getPath()).thenReturn(platformFriendly("/1/2/3/4/5/test.txt"));

        label.valueChanged(event());

        assertEquals(platformFriendly(".../2/3/4/5"), label.getText());
    }

    @Test
    public void pathShouldAlwaysStartWithASlash() {
        when(fileObject.getPath()).thenReturn(platformFriendly("parent/test.txt"));

        label.valueChanged(event());

        assertEquals(platformFriendly("/parent"), label.getText());
    }

    @Test
    public void oneParentDirectory() {
        when(fileObject.getPath()).thenReturn(platformFriendly("/parent/test.txt"));

        label.valueChanged(event());

        assertEquals(platformFriendly("/parent"), label.getText());
    }
    
    @Test
    public void noParentDirectory_StartsWithSlash() {
        when(fileObject.getPath()).thenReturn(platformFriendly("/test.txt"));

        label.valueChanged(event());

        assertEquals("", label.getText());
    }

    @Test
    public void noParentDirectory() {
        when(fileObject.getPath()).thenReturn("test.txt");

        label.valueChanged(event());

        assertEquals("", label.getText());
    }

    @Test
    public void eventSaysThingsAreStillBeingAdjusted() {
        when(fileObject.getPath()).thenReturn(platformFriendly("/parent/test.txt"));

        label.valueChanged(new ListSelectionEvent(list, -1, -2, true));

        assertEquals("", label.getText());
    }

    private String platformFriendly(String path) {
        return path.replace("/", File.separator);
    }

    private ListSelectionEvent event() {
        return new ListSelectionEvent(list, -1, -2, false);
    }

}
