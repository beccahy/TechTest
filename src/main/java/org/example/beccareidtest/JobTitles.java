package org.example.beccareidtest;

/**
 * Enum representing job titles.
 */
public enum JobTitles {
    ARCHITECT("Architect"),
    SOFTWARE_ENGINEER("Software Engineer"),
    QUANTITY_SURVEYOR("Quantity Surveyor"),
    ACCOUNTANT("Accountant");

    private final String jobTitle;

    JobTitles(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Get the job title.
     *
     * @return the job title
     */
    public String getJobTitle() {
        return jobTitle;
    }
}
