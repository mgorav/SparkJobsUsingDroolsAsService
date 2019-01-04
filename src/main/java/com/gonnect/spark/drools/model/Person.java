package com.gonnect.spark.drools.model;


public class Person implements java.io.Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = -7080955793548250917L;
private String firstName;
  private String lastName;

  private int id;
  private int requestAmount;
  private int creditScore;

  private boolean approved;

  public Person(int id, String firstName, String lastName, int requestAmount, int creditScore) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.requestAmount = requestAmount;
    this.creditScore = creditScore;
  }

  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }

  public int getId() { return id; }
  public int getRequestAmount() { return requestAmount; }
  public int getCreditScore() { return creditScore; }

  public boolean isApproved() { return approved; }
  public void setApproved(boolean _approved) { approved = _approved; }
}
