package displays.pid;

class PlayerRejATS1_S {
	private String ID;
	private String FT;
	private String ORG;
	private String TBNM;
	private String MSG;
	private String DTM;
	private String ID_T;
	private String TABEL;
			 
	public PlayerRejATS1_S(String ID, String FT, String ORG,String TBNM, String MSG, String DTM, String ID_T, String TABEL) {
		this.ID = ID;
		this.FT = FT;
		this.ORG = ORG;
		this.TBNM = TBNM;
		this.MSG = MSG;
		this.DTM = DTM;
		this.ID_T = ID_T;
		this.TABEL = TABEL;
	}
	
	public String getID() { return ID; }
	public String getFT() { return FT; }
	public String getORG() { return ORG; }
	public String getTBNM() { return TBNM; }
	public String getMSG() { return MSG; }
	public String getDTM() { return DTM; }
	public String getID_T() { return ID_T; }
	public String getTABEL() { return TABEL; }
}

