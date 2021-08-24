package com.diegunix.meep.commons.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum Error {

  GENERAL_OK("0", "GENERAL"), 
  GENERAL_KO("-999999", "An error has occurred."),
  API_SYNC_ERROR("-3200","Api error.");

  public final String code;
  public final String description;

  private Error(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public String getCode() {
    return code;
  }

  public static Error getByCode(String code) {
    Error error = null;
    for (Error e : Error.values()) {
      if (e.getCode().equals(code)) {
        error = e;
      }
    }
    return error;
  }

  @Override
  public String toString() {
    return "(" + code + ")- " + description;
  }
}