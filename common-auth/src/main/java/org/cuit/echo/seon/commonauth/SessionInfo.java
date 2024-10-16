package org.cuit.echo.seon.commonauth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author SeonExlike
 * @since 2024/4/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@SuppressWarnings("all")
public class SessionInfo {

    private String email;

    private String role;
}
