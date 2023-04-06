package etf.project.model.document;

public class Document {

	private String documentNumber, issuer, name, lastName, note;
	private String validYear;
	
	public Document() {
		super();
	}

	public Document(String documentNumber, String issuer, String name, String lastName, String note, String validYear) {
		super();
		this.documentNumber = documentNumber;
		this.issuer = issuer;
		this.name = name;
		this.lastName = lastName;
		this.note = note;
		this.validYear = validYear;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getValidYear() {
		return validYear;
	}

	public void setValidYear(String validYear) {
		this.validYear = validYear;
	}

	@Override
	public String toString() {
		return "Document "
				+ "[documentNumber=" + documentNumber + ", issuer=" + issuer + ", name=" + name + ", "
						+ "lastName=" + lastName + ", note=" + note + ", validDate=" + validYear + "]";
	}
}
