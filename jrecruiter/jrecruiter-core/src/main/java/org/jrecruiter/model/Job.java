/*
*	http://www.jrecruiter.org
*
*	Disclaimer of Warranty.
*
*	Unless required by applicable law or agreed to in writing, Licensor provides
*	the Work (and each Contributor provides its Contributions) on an "AS IS" BASIS,
*	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied,
*	including, without limitation, any warranties or conditions of TITLE,
*	NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE. You are
*	solely responsible for determining the appropriateness of using or
*	redistributing the Work and assume any risks associated with Your exercise of
*	permissions under this License.
*
*/
package org.jrecruiter.model;

import java.io.Serializable;
import org.jrecruiter.scala.Region;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.jrecruiter.common.Constants.CommongKeyIds;
import org.jrecruiter.common.Constants.JobStatus;
import org.jrecruiter.common.Constants.OfferedBy;
/**
* This class represents a job posting.
*
* @author Gunnar Hillert
* @version $Id$
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"universalId"})})
@Indexed
@Analyzer(impl = org.apache.lucene.analysis.standard.StandardAnalyzer.class)
public class Job implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8966612480290486159L;

    /** Job posting id. */
    @XmlAttribute
    private Long id;

    /** Uniquely identifies the data object. This key will be unique across migrations and is used
     * mainly in places where external services may need to access job posting information. The
     * stored information is typically a UUID but does not have to be (migrating older data sets for
     * example)
     */
    @XmlAttribute
    private String universalId;

    /** Industry. */
    @XmlAttribute @XmlIDREF
    private Industry industry;

    /** Region where the job is located. */
    @XmlAttribute @XmlIDREF
    private Region region;

    /** Owner of job posting) */
    @XmlIDREF
    private User user;

    /** Business name. */
    private String businessName;

    /** If no suitable region can be selected,
     *  allow to provide for a custom region name. */
    private String regionOther;

    /** Job title. */
    private String jobTitle;

    /** Salary. */
    private String salary;

    /** Description of the job posting. */
    private String description;

    /** Website of the company. */
    private String website;

    /** Address field 1. */
    private String businessAddress1;

    /** Address field 2. */
    private String businessAddress2;

    /** City. */
    private String businessCity;

    /** State. */
    private String businessState;

    /** Postal Code. */
    private String businessZip;

    /** Business Phone. */
    private String businessPhone;

    /** Business Phone Extension. */
    private String businessPhoneExtension;

    /** Business Email. */
    private String businessEmail;

    /** Industry - Free Form Text Field. */
    private String industryOther;

    /** Restrictions for the advertised job. */
    private String jobRestrictions;

    /** Job posting creation date. */
    @XmlAttribute
    private Date registrationDate;

    /** Date the job posting was updated. */
    @XmlAttribute
    private Date updateDate;

    /** Status of job posting. */
    @XmlAttribute
    private JobStatus status;

    /** */
    @XmlAttribute
    private OfferedBy offeredBy;

    /** Used to map the Location of the job. */
    private BigDecimal longitude = null;

    /** Used to map the Location of the job.*/
    private BigDecimal latitude = null;

    /** Used to specify the zoom level when showing the location of the job
     *  on a map. */
    private Integer zoomLevel = Integer.valueOf(8);

    /** Statistic of this particuliar job posting. */
    private Statistic statistic;

    /** Flag that indicates whether this job is using google maps to further
     *  details the job's location.
     */
    @XmlAttribute
    private Boolean usesMap;

    //~~~~~Constructors~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** default constructor */
    public Job() {
    }

    /** Constructor with just the job id. */
    public Job(Long jobId) {
        this.id = jobId;
    }

    /**
     * Used for initializing job summaries.
     *
     * @param id
     * @param businessName
     * @param jobTitle
     * @param region
     * @param updateDate
     */
    public Job(final Long id, final String businessName, final String jobTitle, final Region region, final Date updateDate, final Boolean usesMap
            , final BigDecimal latitude, final BigDecimal longitude, final Integer zoomlevel) {
        this.id = id;
        this.businessName = businessName;
        this.jobTitle = jobTitle;
        this.region = region;
        this.updateDate = updateDate;
        this.usesMap = usesMap;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoomLevel = zoomlevel;

    }

    /** minimal constructor */
    public Job(Long id, User user, String businessName, String jobTitle, String description, JobStatus status) {
        this.id = id;
        this.user = user;
        this.businessName = businessName;
        this.jobTitle = jobTitle;
        this.description = description;
        this.status = status;
    }
    /** full constructor */
    public Job(Long id, Industry industry, User user, String businessName,
               String businessLocation, String jobTitle, String salary,
               String description, String website, String businessAddress1,
               String businessAddress2, String businessCity,
               String businessState, String businessZip, String businessPhone,
               String businessEmail, String industryOther,
               String jobRestrictions, Date registrationDate,
               Date expirationDate, Date updateDate, JobStatus status,
               OfferedBy offeredBy, BigDecimal longitude, BigDecimal latitude,
               Statistic statistic) {
       this.id = id;
       this.industry = industry;
       this.user = user;
       this.businessName = businessName;
       this.regionOther = businessLocation;
       this.jobTitle = jobTitle;
       this.salary = salary;
       this.description = description;
       this.website = website;
       this.businessAddress1 = businessAddress1;
       this.businessAddress2 = businessAddress2;
       this.businessCity = businessCity;
       this.businessState = businessState;
       this.businessZip = businessZip;
       this.businessPhone = businessPhone;
       this.businessEmail = businessEmail;
       this.industryOther = industryOther;
       this.jobRestrictions = jobRestrictions;
       this.registrationDate = registrationDate;
       this.updateDate = updateDate;
       this.status = status;
       this.offeredBy = offeredBy;
       this.longitude = longitude;
       this.latitude = latitude;
       this.statistic = statistic;
    }

    /**
     * @return the id
     */
    @Id @GeneratedValue(generator="hibseq")
    @DocumentId
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the industry
     */
    @ManyToOne(cascade={}, fetch=FetchType.LAZY)
    @JoinColumn(name="industries_id", unique=false, nullable=true, insertable=true, updatable=true)
    @IndexedEmbedded
    public Industry getIndustry() {
        return industry;
    }

    /**
     * @param industry the industry to set
     */
    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    /**
     * @return the user
     */
    @ManyToOne(cascade={}, fetch=FetchType.LAZY)
    @JoinColumn(name="regions_id", unique=false, nullable=true, insertable=true, updatable=true)
    @IndexedEmbedded
    @BatchSize(size=15)
    @XmlTransient
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @ManyToOne(cascade={},
            fetch=FetchType.LAZY)
    @JoinColumn(name="users_id", unique=false, nullable=false, insertable=true, updatable=true)
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the businessName
     */
    @Column(unique=false, nullable=false, insertable=true, updatable=true, length=50)
    @Field(index=Index.TOKENIZED, store=Store.YES)
    public String getBusinessName() {
        return businessName;
    }

    /**
     * @param businessName the businessName to set
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * @return the businessLocation
     */
    @Column(name="region_other", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getRegionOther() {
        return regionOther;
    }

    /**
     * @param businessLocation the businessLocation to set
     */
    public void setRegionOther(String businessLocation) {
        this.regionOther = businessLocation;
    }

    /**
     * @return the jobTitle
     */
    @Column(name="job_title", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    @Field(index=Index.TOKENIZED, store=Store.YES)
    @org.hibernate.annotations.Index(name="jobTitleIndex")
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle the jobTitle to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * @return the salary
     */
    @Column(name="salary", unique=false, nullable=true, insertable=true, updatable=true)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     * @return the description
     */
    @Lob
    @Column(name="description", unique=false, nullable=false, insertable=true, updatable=true)
    @Field(index=Index.TOKENIZED, store=Store.YES)
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the website
     */
    @Column(name="website", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the businessAddress1
     */
    @Column(name="business_address1", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getBusinessAddress1() {
        return businessAddress1;
    }

    /**
     * @param businessAddress1 the businessAddress1 to set
     */
    public void setBusinessAddress1(String businessAddress1) {
        this.businessAddress1 = businessAddress1;
    }

    /**
     * @return the businessAddress2
     */
    @Column(name="business_address2", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getBusinessAddress2() {
        return businessAddress2;
    }

    /**
     * @param businessAddress2 the businessAddress2 to set
     */
    public void setBusinessAddress2(String businessAddress2) {
        this.businessAddress2 = businessAddress2;
    }

    /**
     * @return the businessCity
     */
    @Column(name="business_city", unique=false, nullable=true, insertable=true, updatable=true, length=30)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getBusinessCity() {
        return businessCity;
    }

    /**
     * @param businessCity the businessCity to set
     */
    public void setBusinessCity(String businessCity) {
        this.businessCity = businessCity;
    }

    /**
     * @return the businessState
     */
    @Column(name="business_state", unique=false, nullable=true, insertable=true, updatable=true, length=20)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getBusinessState() {
        return businessState;
    }

    /**
     * @param businessState the businessState to set
     */
    public void setBusinessState(String businessState) {
        this.businessState = businessState;
    }

    /**
     * @return the businessZip
     */
    @Column(name="business_zip", unique=false, nullable=true, insertable=true, updatable=true, length=15)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getBusinessZip() {
        return businessZip;
    }

    /**
     * @param businessZip the businessZip to set
     */
    public void setBusinessZip(String businessZip) {
        this.businessZip = businessZip;
    }

    /**
     * @return the businessPhone
     */
    @Column(name="business_phone", unique=false, nullable=true, insertable=true, updatable=true, length=15)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getBusinessPhone() {
        return businessPhone;
    }

    /**
     * @param businessPhone the businessPhone to set
     */
    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    /**
     * @return the businessPhone
     */
    @Column(name="business_phone_extension", unique=false, nullable=true, insertable=true, updatable=true, length=15)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getBusinessPhoneExtension() {
        return businessPhoneExtension;
    }

    /**
     * @param businessPhone the businessPhone to set
     */
    public void setBusinessPhoneExtension(String businessPhoneExtension) {
        this.businessPhoneExtension = businessPhoneExtension;
    }

    /**
     * @return the businessEmail
     */
    @Column(name="business_email", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getBusinessEmail() {
        return businessEmail;
    }

    /**
     * @param businessEmail the businessEmail to set
     */
    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    /**
     * @return the industryOther
     */
    @Column(name="industry_other", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getIndustryOther() {
        return industryOther;
    }

    /**
     * @param industryOther the industryOther to set
     */
    public void setIndustryOther(String industryOther) {
        this.industryOther = industryOther;
    }

    /**
     * @return the jobRestrictions
     */
    @Lob
    @Column(name="job_restrictions", unique=false, nullable=true, insertable=true, updatable=true, length=4000)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    public String getJobRestrictions() {
        return jobRestrictions;
    }

    /**
     * @param jobRestrictions the jobRestrictions to set
     */
    public void setJobRestrictions(String jobRestrictions) {
        this.jobRestrictions = jobRestrictions;
    }

    /**
     * @return the registrationDate
     */
    @Column(name="registration_date", unique=false, nullable=false, insertable=true, updatable=true, length=8)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return the updateDate
     */
    @Column(name="update_date", unique=false, nullable=true, insertable=true, updatable=true, length=8)
    @Temporal(TemporalType.TIMESTAMP)
    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    @DateBridge(resolution = Resolution.DAY)
    @org.hibernate.annotations.Index(name="updateDateIndex")
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return the status
     */
    @Column(name="status", unique=false, nullable=false, insertable=true, updatable=true, precision=2, scale=0)
    public JobStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(JobStatus status) {
        this.status = status;
    }

    /**
     * @return the offeredBy
     */
    @Column(name="offered_by", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)
    public OfferedBy getOfferedBy() {
        return offeredBy;
    }

    /**
     * @param offeredBy the offeredBy to set
     */
    public void setOfferedBy(OfferedBy offeredBy) {
        this.offeredBy = offeredBy;
    }

    /**
     * @return the longitude
     */
    @Column(name="longitude", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=6)
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Column(name="uses_map", unique=false, nullable=true, insertable=true, updatable=true)
    public Boolean getUsesMap() {
        return usesMap;
    }

    public void setUsesMap(Boolean usesMap) {
        this.usesMap = usesMap;
    }

    /**
     * @return the latitude
     */
    @Column(name="latitude", unique=false, nullable=true, insertable=true, updatable=true, precision=12, scale=6)
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(Integer zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    /**
     * @return the statistics
     */
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Statistic getStatistic() {
        return statistic;
    }

    /**
     * @param statistics the statistics to set
     */
    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    /**
     * @return the universalId
     */
    public String getUniversalId() {
        return universalId;
    }

    /**
     * @param universalId the universalId to set
     */
    public void setUniversalId(String universalId) {
        this.universalId = universalId;
    }


    //~~~~~Helper Methods~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    @Transient
    public String getRegionForDisplay() {

        if (this.region == null) {
            return null;
        } else if (CommongKeyIds.OTHER.getId().equals(this.region.getId())) {

            if (this.regionOther != null) {
                return this.regionOther;
            } else {
                return null;
            }

        } else {
            return this.region.getName();
        }
    }

    @Transient
    public String getRegionForDisplayFormatted() {

        if (this.region == null) {
            return null;
        } else if (CommongKeyIds.OTHER.getId().equals(this.region.getId())) {

            if (this.regionOther != null) {
                return "Other (" + this.regionOther + ")";
            } else {
                return null;
            }

        } else {
            return this.region.getName();
        }
    }

    @Transient
    public String getIndustryForDisplay() {

        if (this.industry == null) {
            return null;
        } else if (CommongKeyIds.OTHER.getId().equals(this.industry.getId())) {

            if (this.industryOther != null) {
                return this.industryOther;
            } else {
                return null;
            }

        } else {
            return this.industry.getName();
        }

    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public String toString()
    {
        final String TAB = " | ";

        final StringBuilder retValue = new StringBuilder();

        retValue.append("Job ( ")
            .append(super.toString()).append(TAB)
            .append("id = ").append(this.getId()).append(TAB)
            .append("registrationDate = ").append(this.getRegistrationDate()).append(TAB)
            .append("updateDate = ").append(this.getUpdateDate()).append(TAB)
            .append("jobTitle = ").append(this.getJobTitle()).append(TAB)
            .append(" )");

        return retValue.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((businessAddress1 == null) ? 0 : businessAddress1.hashCode());
        result = prime
                * result
                + ((businessAddress2 == null) ? 0 : businessAddress2.hashCode());
        result = prime * result
                + ((businessCity == null) ? 0 : businessCity.hashCode());
        result = prime * result
                + ((businessEmail == null) ? 0 : businessEmail.hashCode());
        result = prime * result
                + ((businessName == null) ? 0 : businessName.hashCode());
        result = prime * result
                + ((businessPhone == null) ? 0 : businessPhone.hashCode());
        result = prime * result
                + ((businessState == null) ? 0 : businessState.hashCode());
        result = prime * result
                + ((businessZip == null) ? 0 : businessZip.hashCode());
        result = prime * result
                + ((jobTitle == null) ? 0 : jobTitle.hashCode());
        result = prime * result
                + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result
                + ((longitude == null) ? 0 : longitude.hashCode());
        result = prime
                * result
                + ((registrationDate == null) ? 0 : registrationDate.hashCode());
        result = prime * result
                + ((universalId == null) ? 0 : universalId.hashCode());
        result = prime * result
                + ((updateDate == null) ? 0 : updateDate.hashCode());
        result = prime * result + ((website == null) ? 0 : website.hashCode());
        result = prime * result
                + ((zoomLevel == null) ? 0 : zoomLevel.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Job)) {
            return false;
        }

        final Job other = (Job) obj;

        if (businessAddress1 == null) {
            if (other.businessAddress1 != null)
                return false;
        } else if (!businessAddress1.equals(other.businessAddress1)) {
            return false;
        }
        if (businessAddress2 == null) {
            if (other.businessAddress2 != null) {
                return false;
            }
        } else if (!businessAddress2.equals(other.businessAddress2)) {
            return false;
        }
        if (businessCity == null) {
            if (other.businessCity != null) {
                return false;
            }
        } else if (!businessCity.equals(other.businessCity)) {
            return false;
        }
        if (businessEmail == null) {
            if (other.businessEmail != null) {
                return false;
            }
        } else if (!businessEmail.equals(other.businessEmail)) {
            return false;
        }
        if (businessName == null) {
            if (other.businessName != null) {
                return false;
            }
        } else if (!businessName.equals(other.businessName)) {
            return false;
        }
        if (businessPhone == null) {
            if (other.businessPhone != null) {
                return false;
            }
        } else if (!businessPhone.equals(other.businessPhone)) {
            return false;
        }
        if (businessState == null) {
            if (other.businessState != null) {
                return false;
            }
        } else if (!businessState.equals(other.businessState)) {
            return false;
        }
        if (businessZip == null) {
            if (other.businessZip != null) {
                return false;
            }
        } else if (!businessZip.equals(other.businessZip)) {
            return false;
        }
        if (jobTitle == null) {
            if (other.jobTitle != null) {
                return false;
            }
        } else if (!jobTitle.equals(other.jobTitle)) {
            return false;
        }
        if (latitude == null) {
            if (other.latitude != null) {
                return false;
            }
        } else if (!latitude.equals(other.latitude)) {
            return false;
        }
        if (longitude == null) {
            if (other.longitude != null) {
                return false;
            }
        } else if (!longitude.equals(other.longitude)) {
            return false;
        }
        if (registrationDate == null) {
            if (other.registrationDate != null) {
                return false;
            }
        } else if (!registrationDate.equals(other.registrationDate)) {
            return false;
        }
        if (universalId == null) {
            if (other.universalId != null) {
                return false;
            }
        } else if (!universalId.equals(other.universalId)) {
            return false;
        }
        if (updateDate == null) {
            if (other.updateDate != null) {
                return false;
            }
        } else if (!updateDate.equals(other.updateDate)) {
            return false;
        }
        if (website == null) {
            if (other.website != null) {
                return false;
            }
        } else if (!website.equals(other.website)) {
            return false;
        }
        if (zoomLevel == null) {
            if (other.zoomLevel != null) {
                return false;
            }
        } else if (!zoomLevel.equals(other.zoomLevel)) {
            return false;
        }

        return true;
    }

}

