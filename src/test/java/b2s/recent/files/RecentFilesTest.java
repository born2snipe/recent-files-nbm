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

import java.util.ArrayList;
import java.util.Arrays;
import org.openide.loaders.DataObject;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RecentFilesTest {
    private DataObjectUtil dataObjectUtil;
    private RecentFiles recentFiles;
    private ProjectUtil projectUtil;
    
    @Before
    public void setUp() {
        dataObjectUtil = mock(DataObjectUtil.class);
        projectUtil = mock(ProjectUtil.class);

        recentFiles = new RecentFiles(3);
        recentFiles.setDataObjectUtil(dataObjectUtil);
        recentFiles.setProjectUtil(projectUtil);

        when(dataObjectUtil.isValid(isA(DataObject.class))).thenReturn(true);
        when(projectUtil.containingProjectClosed(isA(DataObject.class))).thenReturn(false);
    }

    @Test
    public void multipleFilesDifferentFilenames() {
        DataObject dataObject = mock(DataObject.class);
        DataObject dataObject2 = mock(DataObject.class);

        when(dataObject.getName()).thenReturn("name");
        when(dataObject2.getName()).thenReturn("name1");

        recentFiles.moveToTop(dataObject);
        recentFiles.moveToTop(dataObject2);

        assertEquals(1, recentFiles.filesWithTheSameName("name"));
    }

    @Test
    public void multipleFilesHaveTheSameName_differentCase() {
        DataObject dataObject = mock(DataObject.class);
        DataObject dataObject2 = mock(DataObject.class);

        when(dataObject.getName()).thenReturn("name");
        when(dataObject2.getName()).thenReturn("NAME");

        recentFiles.moveToTop(dataObject);
        recentFiles.moveToTop(dataObject2);

        assertEquals(2, recentFiles.filesWithTheSameName("name"));
    }

    @Test
    public void multipleFilesHaveTheSameName() {
        DataObject dataObject = mock(DataObject.class);
        DataObject dataObject2 = mock(DataObject.class);

        when(dataObject.getName()).thenReturn("name");
        when(dataObject2.getName()).thenReturn("name");

        recentFiles.moveToTop(dataObject);
        recentFiles.moveToTop(dataObject2);

        assertEquals(2, recentFiles.filesWithTheSameName("name"));
    }

    @Test
    public void add_multipleFiles_canNotGoOverMaxCount() {
        DataObject dataObject = mock(DataObject.class);
        DataObject dataObject2 = mock(DataObject.class);
        DataObject dataObject3 = mock(DataObject.class);
        DataObject dataObject4 = mock(DataObject.class);

        recentFiles.moveToTop(dataObject);
        recentFiles.moveToTop(dataObject2);
        recentFiles.moveToTop(dataObject3);
        recentFiles.moveToTop(dataObject4);

        assertEquals(Arrays.asList(dataObject4, dataObject3, dataObject2), recentFiles.asList());
    }

    @Test
    public void add_multipleFiles() {
        DataObject dataObject = mock(DataObject.class);
        DataObject dataObject2 = mock(DataObject.class);

        recentFiles.moveToTop(dataObject);
        recentFiles.moveToTop(dataObject2);

        assertEquals(Arrays.asList(dataObject2, dataObject), recentFiles.asList());
    }

    @Test
    public void add_multipleFiles_oneFileAddedMultipleTimes() {
        DataObject dataObject = mock(DataObject.class);
        DataObject dataObject2 = mock(DataObject.class);

        recentFiles.moveToTop(dataObject);
        recentFiles.moveToTop(dataObject2);
        recentFiles.moveToTop(dataObject);

        assertEquals(Arrays.asList(dataObject, dataObject2), recentFiles.asList());
    }

    @Test
    public void add_singleFile() {
        DataObject dataObject = mock(DataObject.class);
        
        recentFiles.moveToTop(dataObject);

        assertEquals(Arrays.asList(dataObject), recentFiles.asList());
        assertTrue(recentFiles.hasFiles());
    }

    @Test
    public void removeInvalidFiles() {
        DataObject dataObject = mock(DataObject.class);
        when(dataObjectUtil.isValid(isA(DataObject.class))).thenReturn(false);

        recentFiles.moveToTop(dataObject);

        assertEquals(new ArrayList(), recentFiles.asList());
    }

    @Test
    public void add_singleFileMultipleTimes() {
        DataObject dataObject = mock(DataObject.class);

        recentFiles.moveToTop(dataObject);
        recentFiles.moveToTop(dataObject);

        assertEquals(Arrays.asList(dataObject), recentFiles.asList());
    }

    @Test
    public void remove_singleFile() {
        DataObject dataObject = mock(DataObject.class);

        recentFiles.moveToTop(dataObject);
        recentFiles.remove(dataObject);

        assertEquals(0, recentFiles.asList().size());
        assertFalse(recentFiles.hasFiles());
    }

    @Test
    public void remove_multipleFiles() {
        DataObject dataObject = mock(DataObject.class);
        DataObject dataObject2 = mock(DataObject.class);

        recentFiles.moveToTop(dataObject);
        recentFiles.moveToTop(dataObject2);
        recentFiles.remove(dataObject);

        assertEquals(Arrays.asList(dataObject2), recentFiles.asList());
    }

    @Test
    public void containingProjectIsClosed() {
        DataObject dataObject = mock(DataObject.class);

        when(projectUtil.containingProjectClosed(isA(DataObject.class))).thenReturn(true);

        recentFiles.moveToTop(dataObject);

        assertEquals(0, recentFiles.asList().size());
    }
}
