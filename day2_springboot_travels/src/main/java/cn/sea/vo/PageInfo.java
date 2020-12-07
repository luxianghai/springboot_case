package cn.sea.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 用于封装分页信息
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> implements Serializable {

    /**
     * 开始索引 = (当前页-1)*页面大小
     * 总页数= 总记录数%页面大小 == 0 ? (总记录数/页面大小) : (总记录数/页面大小 + 1)
     */
    private Integer start; // 开始索引
    private Integer rows; // 页面大小
    private Integer page; // 当前页
    private Long totals; // 总记录数
    private Integer pageTotals; // 总页数
    private List<T> data; // 查询到的数据
}
