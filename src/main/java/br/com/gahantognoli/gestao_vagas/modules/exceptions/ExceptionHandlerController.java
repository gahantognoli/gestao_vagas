package br.com.gahantognoli.gestao_vagas.modules.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

  private MessageSource messageSource;

  public ExceptionHandlerController(MessageSource messageSource){
    this.messageSource = messageSource;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<ErrorMessageDTO> dto = new ArrayList<>();
    ex.getBindingResult().getFieldErrors().forEach(err -> {
      String message = this.messageSource.getMessage(err, LocaleContextHolder.getLocale());
      dto.add(new ErrorMessageDTO(message, err.getField()));
    });

    return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
  }

}
