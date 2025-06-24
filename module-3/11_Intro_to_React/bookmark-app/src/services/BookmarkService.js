/**
 * This service class is used to mimic the data from the Bookmark Manager server API.
 */
export default {

  getPublicBookmarks() {
    return [
      {
        bookmarkId: 20,
        userId: 4,
        userDisplayName: "troublemaker",
        tags: null,
        title: "===>>> Best Link EVER!!! Changed my world!",
        url: "https://makeameme.org/meme/everything-under-20",
        description:
          "This program will have you living the good life in a month, guaranteed!",
        createDate: "2025-02-05",
        allTags:
          "Animals, Education, Humor, Jobs, Productivity, Sports, Technology",
        public: true,
        flagged: false,
      },
      {
        bookmarkId: 1,
        userId: 2,
        userDisplayName: "Imani",
        tags: null,
        title: "Create a Good LinkedIn Profile",
        url: "https://www.linkedin.com/help/linkedin/answer/a554351/how-do-i-create-a-good-linkedin-profile",
        description:
          "Tips for creating a solid LinkedIn profile for job hunting.",
        createDate: "2024-03-03",
        allTags: "Education, Jobs",
        public: true,
        flagged: false,
      },
      {
        bookmarkId: 4,
        userId: 2,
        userDisplayName: "Imani",
        tags: null,
        title: "Developer Networking Site",
        url: "https://www.invisiblenetwork.com/",
        description: "Networking with double opt-in introductions.",
        createDate: "2025-01-07",
        allTags: "Jobs, Technology",
        public: true,
        flagged: false,
      },
      {
        bookmarkId: 5,
        userId: 2,
        userDisplayName: "Imani",
        tags: null,
        title: "Good Imposter Syndrome Article",
        url: "https://www.themuse.com/advice/5-different-types-of-imposter-syndrome-and-5-ways-to-battle-each-one",
        description: null,
        createDate: "2024-10-09",
        allTags: null,
        public: true,
        flagged: false,
      },
      {
        bookmarkId: 3,
        userId: 2,
        userDisplayName: "Imani",
        tags: null,
        title: "huntr",
        url: "https://huntr.co/",
        description: "Awesome website for tracking job applications.",
        createDate: "2024-03-03",
        allTags: "Jobs",
        public: true,
        flagged: false,
      },
      {
        bookmarkId: 18,
        userId: 4,
        userDisplayName: "troublemaker",
        tags: null,
        title: "Interview Prep",
        url: "https://makeameme.org/meme/everything-under-20",
        description: "20 Tips for better interviews.",
        createDate: "2025-02-04",
        allTags: "Jobs",
        public: true,
        flagged: true,
      },
      {
        bookmarkId: 19,
        userId: 4,
        userDisplayName: "troublemaker",
        tags: null,
        title: "Learn to code for free!",
        url: "https://makeameme.org/meme/everything-under-20",
        description: "Why buy that course when you can learn for free!",
        createDate: "2025-02-04",
        allTags: "Education, Technology",
        public: true,
        flagged: true,
      },
      {
        bookmarkId: 2,
        userId: 2,
        userDisplayName: "Imani",
        tags: null,
        title: "Step by Step Resume Guide",
        url: "https://www.themuse.com/advice/how-to-make-a-resume-examples",
        description: "Nice step by step resume guide with examples.",
        createDate: "2024-03-03",
        allTags: "Jobs",
        public: true,
        flagged: false,
      },
    ];
  },

  getNoBookmarks() {
    return [];
  },

};
