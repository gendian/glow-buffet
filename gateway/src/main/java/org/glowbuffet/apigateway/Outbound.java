package org.glowbuffet.apigateway;

import org.glowbuffet.common.dto.Resolution;

public record Outbound(
        long id,
        Resolution resolution) { }
