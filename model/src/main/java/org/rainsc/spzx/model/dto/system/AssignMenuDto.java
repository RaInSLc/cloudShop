package org.rainsc.spzx.model.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Schema(description = "请求参数实体类")
public class AssignMenuDto {
    // 角色id
    @Schema(description = "角色id")
    private Long roleId;

    // 选中的菜单id的集合; Map的键表示菜单的id，值表示是否为半开; 0否，1是
    @Schema(description = "选中的菜单id的集合")
    private List<Map<String, Number>> menuIdList;
}