package jp.co.sss.lms.form;

	public class Attendance {
	    private Integer studentAttendanceId;
	    private String trainingDate;
	    private String trainingStartTime;
	    private String trainingEndTime;
	    private Integer blankTime;
	    private String note;
	    private String sectionName;
	    private String status;
	    private String dispTrainingDate;
	    private String statusDispName;
	    private Boolean isToday;


	    // コンストラクタ
	    public Attendance() {}

	    // getter / setter

	    public Integer getStudentAttendanceId() {
	        return studentAttendanceId;
	    }
	    public void setStudentAttendanceId(Integer studentAttendanceId) {
	        this.studentAttendanceId = studentAttendanceId;
	    }

	    public String getTrainingDate() {
	        return trainingDate;
	    }
	    public void setTrainingDate(String trainingDate) {
	        this.trainingDate = trainingDate;
	    }

	    public String getTrainingStartTime() {
	        return trainingStartTime;
	    }
	    public void setTrainingStartTime(String trainingStartTime) {
	        this.trainingStartTime = trainingStartTime;
	    }

	    public String getTrainingEndTime() {
	        return trainingEndTime;
	    }
	    public void setTrainingEndTime(String trainingEndTime) {
	        this.trainingEndTime = trainingEndTime;
	    }

	    public Integer getBlankTime() {
	        return blankTime;
	    }
	    public void setBlankTime(Integer blankTime) {
	        this.blankTime = blankTime;
	    }

	    public String getNote() {
	        return note;
	    }
	    public void setNote(String note) {
	        this.note = note;
	    }

	    public String getSectionName() {
	        return sectionName;
	    }
	    public void setSectionName(String sectionName) {
	        this.sectionName = sectionName;
	    }

	    public String getStatus() {
	        return status;
	    }
	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getDispTrainingDate() {
	        return dispTrainingDate;
	    }
	    public void setDispTrainingDate(String dispTrainingDate) {
	        this.dispTrainingDate = dispTrainingDate;
	    }

	    public String getStatusDispName() {
	        return statusDispName;
	    }
	    public void setStatusDispName(String statusDispName) {
	        this.statusDispName = statusDispName;
	    }

	    public Boolean getIsToday() {
	        return isToday;
	    }
	    public void setIsToday(Boolean isToday) {
	        this.isToday = isToday;
	    }

	}


