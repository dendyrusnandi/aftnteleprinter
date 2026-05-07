package displays.pid;

class PlayerOutATS1_S {
	private String FT;
	private String ST;
	private String DTM;
	private String ARCID;
	private String TYP;
	private String ORG;
	private String MSG;
	private String ID;
	private String TABEL;
	private String DATETIME;
			 
	public PlayerOutATS1_S(String FT, String ST, String DTM,String ARCID, String TYP, String ORG, String MSG, String ID, String TABEL, String DATETIME) {
		this.FT = FT;
		this.ST = ST;
		this.DTM = DTM;
		this.ARCID = ARCID;
		this.TYP = TYP;
		this.ORG = ORG;
		this.MSG = MSG;
		this.ID = ID;
		this.TABEL = TABEL;
		this.DATETIME = DATETIME;
	}
	
	public String getFT() { return FT; }
	public String getST() { return ST; }
	public String getDTM() { return DTM; }
	public String getARCID() { return ARCID; }
	public String getTYP() { return TYP; }
	public String getORG() { return ORG; }
	public String getMSG() { return MSG; }
	public String getID() { return ID; }
	public String getTABEL() { return TABEL; }
	public String getDATETIME() { return DATETIME; }
}

