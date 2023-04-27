package com.geostar.geoonline.entityserverhtml.framework.common.sys;

public class JsonResult {
  private boolean status;
  
  private String code;
  
  private String message;
  
  public boolean equals(Object o) {
    if (o == this)
      return true; 
    if (!(o instanceof JsonResult))
      return false; 
    JsonResult other = (JsonResult)o;
    if (!other.canEqual(this))
      return false; 
    if (isStatus() != other.isStatus())
      return false; 
    Object this$code = getCode(), other$code = other.getCode();
    if ((this$code == null) ? (other$code != null) : !this$code.equals(other$code))
      return false; 
    Object this$message = getMessage(), other$message = other.getMessage();
    return !((this$message == null) ? (other$message != null) : !this$message.equals(other$message));
  }
  
  protected boolean canEqual(Object other) {
    return other instanceof JsonResult;
  }
  
  public int hashCode() {
    int PRIME = 59;
    int result = 1;
    result = result * 59 + (isStatus() ? 79 : 97);
    Object $code = getCode();
    result = result * 59 + (($code == null) ? 43 : $code.hashCode());
    Object $message = getMessage();
    return result * 59 + (($message == null) ? 43 : $message.hashCode());
  }
  
  public String toString() {
    return "JsonResult(status=" + isStatus() + ", code=" + getCode() + ", message=" + getMessage() + ")";
  }
  
  public boolean isStatus() {
    return this.status;
  }
  
  public void setStatus(boolean status) {
    this.status = status;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
}
