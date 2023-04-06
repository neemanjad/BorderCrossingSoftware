package etf.project.model.document;

import java.util.Date;

public class Document {

	private String documentNumber, issuer, name, lastName, note;
	private Date validDate;
	
	public Document() {
		super();
	}

	public Document(String documentNumber, String issuer, String name, String lastName, String note, Date validDate) {
		super();
		this.documentNumber = documentNumber;
		this.issuer = issuer;
		this.name = name;
		this.lastName = lastName;
		this.note = note;
		this.validDate = validDate;
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

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Override
	public String toString() {
		return "Document "
				+ "[documentNumber=" + documentNumber + ", issuer=" + issuer + ", name=" + name + ", "
						+ "lastName=" + lastName + ", note=" + note + ", validDate=" + validDate + "]";
	}
}
