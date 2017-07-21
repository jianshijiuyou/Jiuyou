package info.jiuyou.jiuyou.data;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/6/9 0009  10:25
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public class SettingsMemento {
    private String theme;
    private String themeText;

    public String getTheme() {
        return theme;
    }

    public SettingsMemento setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public String getThemeText() {
        return themeText;
    }

    public SettingsMemento setThemeText(String themeText) {
        this.themeText = themeText;
        return this;
    }
}
