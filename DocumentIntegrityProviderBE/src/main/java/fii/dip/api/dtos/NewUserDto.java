package fii.dip.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class NewUserDto {
    @NotBlank
    String email;
    @NotBlank
    String password;
}
