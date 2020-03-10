package in.taskoo.backbone.offer.dto;

import in.taskoo.backbone.user.dto.User;
import lombok.Data;

@Data
public class Offer {
  private Long id;
  private User user;
  private Integer amount;
  private Long taskId;
}
