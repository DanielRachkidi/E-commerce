package com.commerce.exceptions;

public class ServiceException
  extends RuntimeException
{
  public ServiceException(String message)
  {
    super(message);
  }
}
