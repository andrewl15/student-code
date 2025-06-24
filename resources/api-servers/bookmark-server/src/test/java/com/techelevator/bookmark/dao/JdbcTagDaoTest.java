package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.exception.DaoException;
import com.techelevator.bookmark.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcTagDaoTest extends BaseDaoTest {

    private static final Tag TAG_1 = new Tag(200, "Tag 3");
    private static final Tag TAG_2 = new Tag(201, "Tag 2");
    private static final Tag TAG_3 = new Tag(202, "Tag 1");
    private static final Tag TAG_4 = new Tag(203, "Tag 4");
    // Get should sort Tags by name
    private static final List<Tag> ALL_TAGS = Arrays.asList(TAG_3, TAG_2, TAG_1, TAG_4);

    private JdbcTagDao dao;

    @BeforeEach
    public void setup() {
        dao = new JdbcTagDao(dataSource);
    }

    @Test
    public void getTags_returns_correct_list_sorted_by_name() {
        List<Tag> result = dao.getTags();
        assertNotNull(result, "getTags returned null");
        assertTagListsMatch(ALL_TAGS, result, "getTags returned wrong or partial data");
    }

    @Test
    public void getTagById_returns_correct_tag() {
        Tag result = dao.getTagById(TAG_1.getId());
        assertNotNull(result, "getTagById returned null");
        assertTagsMatch(TAG_1, result, "getTagById returned wrong or partial data");
    }

    @Test
    public void getTagById_returns_null_when_id_not_found() {
        Tag result = dao.getTagById(9999);
        assertNull(result, "getTagById failed to return null for id not in database");
    }

    @Test
    public void createTag_returns_tag_with_id_and_correct_values() {
        Tag newTag = new Tag(0, "New Tag");
        Tag createdTag = dao.createTag(newTag);

        assertNotNull(createdTag, "createTag returned null");

        int newId = createdTag.getId();
        assertTrue(newId > 0, "createTag returned a tag without an id");

        newTag.setId(newId);
        assertTagsMatch(newTag, createdTag, "createTag returned a tag with wrong or partial data");
    }

    @Test
    public void createdTag_tag_has_expected_values_when_retrieved() {
        Tag newTag = new Tag(0, "New Tag");
        Tag createdTag = dao.createTag(newTag);

        int newId = createdTag.getId();
        Tag retrievedTag = dao.getTagById(newId);

        assertTagsMatch(createdTag, retrievedTag, "createTag did not save correct data in database");
    }

    @Test
    public void updateTag_returns_tag_with_correct_values() {
        Tag tag = new Tag();
        tag.setId(TAG_1.getId());
        tag.setName("Modified Tag");

        Tag updatedTag = dao.updateTag(tag);

        assertTagsMatch(updatedTag, tag, "updateTag did not save correct data in database");
    }

    @Test
    public void updateTag_has_expected_values_when_retrieved() {
        Tag tag = new Tag();
        tag.setId(TAG_1.getId());
        tag.setName("Modified Tag");

        Tag updatedTag = dao.updateTag(tag);
        int newId = updatedTag.getId();
        Tag retrievedTag = dao.getTagById(newId);

        assertTagsMatch(updatedTag, retrievedTag, "updateTag did not save correct data in database");
    }

    @Test
    public void deleteTag_removes_tag_with_no_bookmarks() {
        dao.deleteTagById(TAG_4.getId());
        Tag tag = dao.getTagById(TAG_4.getId());
        assertNull(tag, "deleteTagById failed to removed tag from database");

        List<Tag> tags = dao.getTags();
        assertEquals(ALL_TAGS.size()-1, tags.size(), "deleteTagById removed the wrong number of tags");
    }

    @Test
    public void deleteTag_does_not_remove_tag_with_bookmark_associations() {
        try {
            // Expect this to fail with exception as bookmarks associated to this tag
            dao.deleteTagById(TAG_1.getId());
            // Assert - fail if exception was not thrown
            fail("deleteTagById did not throw exception when trying to delete tag associated with bookmarks.");
        } catch (DaoException e) {
            // Do nothing, this is the expected behavior
        }
   }

    @Test
    public void deleteTag_does_not_error_when_id_not_found() {
        try {
            dao.deleteTagById(9999);
            List<Tag> tags = dao.getTags();
            assertEquals(ALL_TAGS.size(), tags.size(), "no tags should have been deleted");

        } catch (Exception e) {
            fail("deleteTagById should not error when not found.");
        }
    }


    private void assertTagsMatch(Tag expected, Tag actual, String messagePrefix) {
        assertEquals(expected.getId(), actual.getId(),messagePrefix + ": Tag id field does not match expected value.");
        assertEquals(expected.getName(), actual.getName(),
                messagePrefix + ": Tag (id=" + expected.getId() + ") contains unexpected data in field 'name'.");
    }

    private void assertTagListsMatch(List<Tag> expectedList, List<Tag> actualList, String messagePrefix) {
        assertEquals(expectedList.size(), actualList.size(), messagePrefix + ": List size incorrect.");
        for (int i = 0; i < expectedList.size(); i++) {
            Tag expected = expectedList.get(i);
            Tag actual = actualList.get(i);
            if (i == 0) {
                // For first item, if ids do not match, may indicate bad sorting
                assertEquals(expected.getId(), actual.getId(),
                        messagePrefix + ": first tag id does not match expected, check sort order");
            }
            assertTagsMatch(expected, actual, messagePrefix);
        }
    }
}
