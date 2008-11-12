package org.jrecruiter.web.actions.admin;

import java.util.List;

import org.jrecruiter.common.CollectionUtils;
import org.jrecruiter.model.Job;
import org.jrecruiter.web.actions.BaseAction;
import org.jrecruiter.web.interceptor.RetrieveMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.texturemedia.smarturls.Result;
import org.texturemedia.smarturls.Results;

/**
 * List all the jobs.
 *
 * @author Gunnar Hillert
 * @version $Id$
 *
 */

@Results(
    {
        @Result(name="inputRedirected", location="show-jobs", type="redirectAction"),
        @Result(name="success", location="index", type="redirectAction")
    }
)
public class ShowJobsAction extends BaseAction {

    /** serialVersionUID. */
    private static final long serialVersionUID = -6536348867574805926L;

    /** Logger Declaration. */
    private final static Logger LOGGER = LoggerFactory.getLogger(ShowJobsAction.class);

    List<Job> jobs;

    List<Long> jobsToDelete = CollectionUtils.getArrayList();

    Boolean displayAjax = Boolean.FALSE;

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    /**
     *
     */
    @RetrieveMessages
    public String execute() {
        this.jobs = jobService.getJobs();
        return INPUT;
    }

    public String ajaxCall() {
        this.jobs = jobService.getUsersJobs(super.getLoggedInUser().getUsername());

        if (displayAjax) {
            return "ajax";
        }

        return null;
    }

    public void prepare() throws Exception {

        ajaxCall();

    }

    public Boolean getDisplayAjax() {
        return displayAjax;
    }

    /**
     *  Delete any selected jobs.
     */
    public String delete() {

         if(!jobsToDelete.isEmpty()){
             for (Long jobId : jobsToDelete) {
                 if (jobId != null) {
                     jobService.deleteJobForId(jobId);
                 }
             }
         }

         super.addActionMessage(getText("job.delete.success"));
         return "inputRedirected";
    }

    public List<Long> getJobsToDelete() {
        return jobsToDelete;
    }

    public void setJobsToDelete(List<Long> jobsToDelete) {
        this.jobsToDelete = jobsToDelete;
    }
}