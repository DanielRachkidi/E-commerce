package Exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

public class DuplicateException
  extends DataIntegrityViolationException
{
  
  public DuplicateException(String message)
  {
    super(message);
  }
}


