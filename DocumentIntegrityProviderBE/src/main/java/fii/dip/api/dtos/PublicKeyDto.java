package fii.dip.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PublicKeyDto {
    @NotBlank
    private String publicKey;
}
