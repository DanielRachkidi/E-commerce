package com.commerce.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateException
  extends DataIntegrityViolationException
{
  public DuplicateException(String message)
  {
    super(message);
  }
}


