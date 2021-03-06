package in.taskoo.backbone.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class InvalidTaskerException extends RuntimeException {
  private static final long serialVersionUID = -6879407276858514246L;
  private String detail;
}
