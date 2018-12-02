package entity;


public class Item {
    private int id;
    private String theme;
    private String optionIds;
    private String startTime;
    private String stopTime;
    private String type;
    private int number;
    private String isStop;
    private String isWaiver;
    private String isOppose;
    private int user_id;
    private int count;
   
	private String userName;
    private int AllVoteCount;//投票总数
    private int status;//1即将开始2点击投票3已经截止

    public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAllVoteCount() {
        return AllVoteCount;
    }

    public void setAllVoteCount(int allVoteCount) {
        AllVoteCount = allVoteCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(String optionIds) {
        this.optionIds = optionIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsStop() {
        return isStop;
    }

    public void setIsStop(String isStop) {
        this.isStop = isStop;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getIsWaiver() {
		return isWaiver;
	}

	public void setIsWaiver(String isWaiver) {
		this.isWaiver = isWaiver;
	}

	public String getIsOppose() {
		return isOppose;
	}

	public void setIsOppose(String isOppose) {
		this.isOppose = isOppose;
	}

}
