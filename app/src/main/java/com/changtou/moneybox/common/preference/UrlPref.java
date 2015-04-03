package com.changtou.moneybox.common.preference;//
//package com.changtou.moneybox.common.preference;
//
//import android.content.Context;
//
//import com.framework.common.preference.PreferenceOpenHelper;
//
///**
// * 描述:网络接口获取类
// *
// * @author chenys
// * @since 2013-9-23 上午11:24:39
// */
//public class UrlPref extends PreferenceOpenHelper {
//
//    private static UrlPref mUrlPref;
//
//    public UrlPref(Context context, String prefname) {
//        super(context, prefname);
//    }
//
//    public synchronized static UrlPref getInstance() {
//        if (mUrlPref == null) {
//            Context context = FJoysMarketApplication.getInstance();
//            String name = "urls";
//            mUrlPref = new UrlPref(context, name);
//        }
//        return mUrlPref;
//    }
//
//    @Override
//    protected String getString(String key, String value) {
//        String result = super.getString(key, value);
//        if (result.startsWith("http://api.4joys.com")) {
//            result = result.replace("http://api.4joys.com",
//                    "http://" + FJoysMarketApplication.getApiIP());
//        }
//        return result;
//    }
//
//    /**
//     * 检查更新接口
//     *
//     * @return
//     */
//    public String getUpdateUrl() {
//        return getString(Keys.UPDATE_URL, getContext().getString(R.string.update_url));
//    }
//
//    /**
//     * 获取应用列表接口
//     *
//     * @return
//     */
//    public String getAppsUrl() {
//        return getString(Keys.APPS_URL, getContext().getString(R.string.apps_url));
//    }
//
//    public String getClassifyUrl() {
//        return getString(Keys.CLASSIFY_URL, getContext().getString(R.string.classify_url));
//    }
//
//    /**
//     * 获取焦点图组接口
//     *
//     * @return
//     */
//    public String getFocusImagesUrl() {
//        return getString(Keys.APPS_URL, getContext().getString(R.string.focus_images_url));
//    }
//
//    /**
//     * 获取推荐html5接口
//     *
//     * @return
//     */
//    public String getRecommendImagesUrl() {
//        return getString(Keys.APPS_URL, getContext().getString(R.string.recommend_images_url));
//    }
//
//    /**
//     * 获取推荐html5游戏列表接口
//     *
//     * @return
//     */
//    public String getH5Url() {
//        return getString(Keys.APPS_URL, getContext().getString(R.string.h5_url));
//    }
//
//    /**
//     * 获取热门关键字接口
//     *
//     * @return
//     */
//    public String getSearchHotKeysUrl() {
//        return getString(Keys.SEARCH_HOT_KEYS_URL,
//                getContext().getString(R.string.search_hot_keys_url));
//    }
//
//    /**
//     * 获取搜索关键字联想接口
//     *
//     * @return
//     */
//    public String getSearchAssociatedKeysUrl() {
//        return getString(Keys.SEARCH_HOT_KEYS_URL,
//                getContext().getString(R.string.search_associated_keys_url));
//    }
//
//    /**
//     * 获取有礼包的应用程序接口
//     *
//     * @return
//     */
//    public String getGiftBagAppsUrl() {
//        return getString(Keys.GIFT_BAG_APPS_URL, getContext().getString(R.string.gift_bag_apps_url));
//    }
//
//    /**
//     * 获取网游活动列表接口
//     *
//     * @return
//     */
//    public String getActionAppsUrl() {
//        // return getString(Keys.ACTION_APPS_URL,
//        // getContext().getString(R.string.action_apps_url));
//        return null;
//    }
//
//    /**
//     * 网游活动详情接口
//     *
//     * @return
//     */
//    public String getActionDetailUrl() {
//        // return getString(Keys.ACTION_DETAIL_URL,
//        // getContext().getString(R.string.action_detail_url));
//        return null;
//    }
//
//    /**
//     * 礼包详情接口
//     *
//     * @return
//     */
//    public String getGiftBagDetailUrl() {
//        // return getString(Keys.GIFT_BAG_DETAIL_URL,
//        // getContext().getString(R.string.gift_bag_detail_url));
//        return null;
//    }
//
//    /**
//     * 领取礼包接口
//     *
//     * @return
//     */
//    public String getGiftBagCDKeyUrl() {
//        // return getString(Keys.GIFT_BAG_CDKEY_URL,
//        // getContext().getString(R.string.gift_bag_cdkey_url));
//        return null;
//    }
//
//    /**
//     * 合集列表接口
//     *
//     * @return
//     */
//    public String getCompilationsUrl() {
//        return getString(Keys.COMPILATIONS_URL, getContext().getString(R.string.compilations_url));
//    }
//
//    /**
//     * 资讯列表接口
//     *
//     * @return
//     */
//    public String getInformationsUrl() {
//        return getString(Keys.INFORATIONS_URL, getContext().getString(R.string.informations_url));
//    }
//
//    /**
//     * 游戏详情接口
//     *
//     * @return
//     */
//    public String getGameDetailUrl() {
//        return getString(Keys.GAME_DETAIL_URL, getContext().getString(R.string.game_detail_url));
//    }
//
//    /**
//     * 游戏攻略列表接口
//     *
//     * @return
//     */
//    public String getStrategiesUrl() {
//        return getString(Keys.STRATEGIES_URL, getContext().getString(R.string.strategies_url));
//    }
//
//    /**
//     * 游戏攻略详情接口
//     *
//     * @return
//     */
//    public String getStrategyDetailUrl() {
//        return getString(Keys.STRATEGY_DETAIL_URL,
//                getContext().getString(R.string.strategy_detail_url));
//    }
//
//    public String getZiXunUrl() {
//        return getString(Keys.STRATEGY_DETAIL_URL,
//                getContext().getString(R.string.strategy_detail_url));
//    }
//
//    public String getSearchAppsUrl() {
//        return getString(Keys.SEARCH_APPS_URL, getContext().getString(R.string.search_apps_url));
//    }
//
//    public String getNewGameServerUrl() {
//        // return getString(Keys.NEW_GAME_SERVER_URL,
//        // getContext().getString(R.string.new_game_server_url));
//        return null;
//    }
//
//    /**
//     * 登录接口
//     *
//     * @return
//     */
//    public String getLoginUrl() {
//        // return getString(Keys.LOGIN_URL,
//        // getContext().getString(R.string.login_url));
//        return null;
//    }
//
//    /**
//     * 手机号是否已注册验证接口
//     *
//     * @return
//     */
//    public String getPhoneNumExistCheckUrl() {
//        // return getString(Keys.PHONE_NUM_EXIST_CHECK_URL,
//        // getContext().getString(R.string.check_phone_num_exist_url));
//        return null;
//    }
//
//    /**
//     * 手机注册获取验证码接口
//     *
//     * @return
//     */
//    public String getValidateCodeUrl() {
//        // return getString(Keys.GET_VALIDATE_CODE_URL,
//        // getContext().getString(R.string.identify_code_url));
//        return null;
//    }
//
//    /**
//     * 手机注册确认手机验证码接口
//     *
//     * @return
//     */
//    public String getConfirmValidateCodeUrl() {
//        // return getString(Keys.CONFIRM_VALIDATE_CODE_URL,
//        // getContext().getString(R.string.confirm_identify_code_url));
//        return null;
//    }
//
//    /**
//     * 手机注册设置密码并完成注册接口
//     *
//     * @return
//     */
//    public String getConfirmValidateAndFinishRegUrl() {
//        // return getString(Keys.CONFIRM_VALIDATE_CODE_AND_FINISH_REG_URL,
//        // getContext().getString(R.string.identify_code_confirm_and_register_url));
//        return null;
//    }
//
//    /**
//     * 账号注册接口
//     *
//     * @return
//     */
//    public String getRegByAccountUrl() {
//        // return getString(Keys.REG_BY_ACCOUNT_URL,
//        // getContext().getString(R.string.register_account_url));
//        return null;
//    }
//
//    /**
//     * 修改账户信息接口(POST)
//     *
//     * @return
//     */
//    public String getModifyAccountInfoUrl() {
//        // return getString(Keys.MODIFY_ACCOUNT_INFO_URL,
//        // getContext().getString(R.string.modify_account_info_url));
//        return null;
//    }
//
//    /**
//     * 获取我的消息列表接口
//     *
//     * @return
//     */
//    public String getMessageListUrl() {
//        // return getString(Keys.MESSAGE_LIST_URL,
//        // getContext().getString(R.string.message_list_url));
//        return null;
//    }
//
//    /**
//     * 获取我的消息详情接口
//     *
//     * @return
//     */
//    public String getMessageDetailUrl() {
//        // return getString(Keys.MESSAGE_DETAIL_URL,
//        // getContext().getString(R.string.message_list_detail_url));
//        return null;
//    }
//
//    /**
//     * 上传头像接口
//     *
//     * @return
//     */
//    public String getUploadHeadUrl() {
//        // return getString(Keys.UPLOAD_HEAD_URL,
//        // getContext().getString(R.string.user_head_url));
//        return null;
//    }
//
//    /**
//     * 修改密码接口
//     *
//     * @return
//     */
//    public String getModifyPsdUrl() {
//        // return getString(Keys.MODIFY_PSD_URL,
//        // getContext().getString(R.string.modify_psd_url));
//        return null;
//    }
//
//    /**
//     * 绑定邮箱接口
//     *
//     * @return
//     */
//    public String getBindEmailUrl() {
//        // return getString(Keys.BIND_EMAL_URL,
//        // getContext().getString(R.string.bind_email_url));
//        return null;
//    }
//
//    /**
//     * 找回密码获取验证码接口
//     *
//     * @return
//     */
//    public String getRetrievePsdUrl() {
//        // return getString(Keys.RETRIEVE_PSD_URL,
//        // getContext().getString(R.string.retrieved_psd_url));
//        return null;
//    }
//
//    /**
//     * 找回密码确认接口
//     *
//     * @return
//     */
//    public String getRetrievePsdCheckUrl() {
//        // return getString(Keys.RETRIEVE_PSD_CHECK_URL,
//        // getContext().getString(R.string.retrieve_check_url));
//        return null;
//    }
//
//    /**
//     * 找回密码重新设置密码接口
//     *
//     * @return
//     */
//    public String getRetrieveResetPsdUrl() {
//        // return getString(Keys.RETRIEVE_RESET_PSD_URL, getContext()
//        // .getString(R.string.reset_psd_url));
//        return null;
//    }
//
//    /**
//     * 绑定手机获取验证码接口
//     *
//     * @return
//     */
//    public String getBindPhoneUrl() {
//        // return getString(Keys.BIND_PHONE_URL,
//        // getContext().getString(R.string.bind_phone_url));
//        return null;
//    }
//
//    /**
//     * 绑定手机确认验证码接口
//     *
//     * @return
//     */
//    public String getBindPhoneCheckUrl() {
//        // return getString(Keys.BIND_PHONE_CHECK_URL,
//        // getContext().getString(R.string.bind_phone_check_url));
//        return null;
//    }
//
//    /**
//     * 修改绑定手机密码确认接口
//     *
//     * @return
//     */
//    public String getBindPhoneModifyCheckPsdUrl() {
//        // return getString(Keys.BIND_PHONE_MODIFY_CHECK_PSD_URL,
//        // getContext().getString(R.string.bind_phone_modify_check_psd_url));
//        return null;
//    }
//
//    /**
//     * 游戏识别接口
//     *
//     * @return
//     */
//    public String getAppCheckUrl() {
//        return getString(Keys.APP_CHECK_URL, getContext().getString(R.string.app_check_url));
//    }
//
//    /**
//     * 游戏评论列表接口
//     *
//     * @return
//     */
//    public String getGameCommentsUrl() {
//        return getString(Keys.GAME_COMMENT_REPLY_URL,
//                getContext().getString(R.string.game_comments_url));
//    }
//
//    /**
//     * 游戏评论列表回复评论
//     *
//     * @return
//     */
//    public String getGameCommentReplyUrl() {
//        return getString(Keys.GAME_COMMENTS_URL,
//                getContext().getString(R.string.game_comment_reply_url));
//    }
//
//    /**
//     * 游戏评论列表赞和踩
//     *
//     * @return
//     */
//    public String getGameCommentAgreeUrl() {
//        return getString(Keys.GAME_COMMENT_AGREE_URL,
//                getContext().getString(R.string.game_comment_agree_url));
//    }
//
//    /**
//     * 检查游戏评论的回复
//     *
//     * @return
//     */
//    public String getCheckCommentReplyUrl() {
//        return getString(Keys.CHECK_COMMENT_REPLY_URL,
//                getContext().getString(R.string.check_comment_reply_url));
//    }
//
//    /**
//     * 发表攻略接口
//     *
//     * @return
//     */
//    public String getPulicStrategyUrl() {
//        // return getString(Keys.PUBLIC_STRATEGY_URL,
//        // getContext().getString(R.string.public_strategy_url));
//        return null;
//    }
//
//    /**
//     * 发表评论接口
//     *
//     * @return
//     */
//    public String getPulicCommentUrl() {
//        return getString(Keys.PUBLIC_COMMENT_URL,
//                getContext().getString(R.string.public_comment_url));
//    }
//
//    /**
//     * 收藏游戏接口
//     *
//     * @return
//     */
//    public String getCollectUrl() {
//        return getString(Keys.COLLECT_URL, getContext().getString(R.string.collect_url));
//
//    }
//
//    /**
//     * 删除收藏接口
//     *
//     * @return
//     */
//    public String getDeleteCollectUrl() {
//        // return getString(Keys.DELETE_COLLECT_URL,
//        // getContext().getString(R.string.delete_collect_url));
//        return null;
//    }
//
//    public String getH5ShareLinkUrl() {
//        return getString(Keys.H5_SHARE_LINK_URL, getContext().getString(R.string.h5_share_link_url));
//    }
//
//    /**
//     * 获取个人收藏列表接口
//     *
//     * @return
//     */
//    public String getCollectListUrl() {
//        return getString(Keys.COLLECT_LIST_URL, getContext().getString(R.string.collect_list_url));
//
//    }
//
//    /**
//     * 合集详情接口
//     *
//     * @return
//     */
//    public String getCompilationsDetailUrl() {
//        return getString(Keys.COMPILATIONS_DETAIL_URL,
//                getContext().getString(R.string.compilations_detail_url));
//    }
//
//    /**
//     * 新服开放列表接口
//     *
//     * @return
//     */
//    public String getNewServerUrl() {
//        // return getString(Keys.NEW_SERVER_URL,
//        // getContext().getString(R.string.new_server_url));
//        return null;
//    }
//
//    /**
//     * 新服开放详情接口
//     *
//     * @return
//     */
//    public String getNewServerDetailUrl() {
//        // return getString(Keys.NEW_SERVER_DETAIL_URL,
//        // getContext().getString(R.string.new_server_detail_url));
//        return null;
//    }
//
//    /**
//     * 统计接口
//     *
//     * @return
//     */
//    public String getStatisticsUrl() {
//        return getString(Keys.STATISTICS_URL, getContext().getString(R.string.statistics_url));
//    }
//
//    public String getPushUrl() {
//        return getString(Keys.PUSH_DATA_URL, getContext().getString(R.string.pull_url));
//    }
//
//    public String GetSplashBgImageUrl() {
//        return getString(Keys.SPLASH_BACKGRUAND_IMAGE_URL,
//                getContext().getString(R.string.splash_backgruand_image_url));
//    }
//
//    public static interface Keys {
//
//        public static final String UPDATE_URL = "update_url";
//
//        public static final String APPS_URL = "apps_url";
//
//        public static final String CLASSIFY_URL = "classify_url";
//
//        public static final String SEARCH_APPS_URL = "search_apps_url";
//
//        public static final String SEARCH_HOT_KEYS_URL = "search_hot_keys";
//
//        public static final String SEARCH_ASSOCIATED_KEYS_URL = "search_associated_keys";
//
//        public static final String NEW_GAME_SERVER_URL = "new_game_server_url";
//
//        public static final String GIFT_BAG_APPS_URL = "gift_bag_apps_url";
//
//        public static final String ACTION_APPS_URL = "action_apps_url";
//
//        public static final String ACTION_DETAIL_URL = "action_detail_url";
//
//        public static final String GIFT_BAG_DETAIL_URL = "gift_bag_detail_url";
//
//        public static final String COLLECT_LIST_URL = "collect_list_url";
//
//        public static final String LOGIN_URL = "login_url";
//
//        public static final String GET_VALIDATE_CODE_URL = "get_validate_code_url";
//
//        public static final String CONFIRM_VALIDATE_CODE_URL = "confirm_validate_code_url";
//
//        public static final String CONFIRM_VALIDATE_CODE_AND_FINISH_REG_URL = "confirm_validate_code_and_finish_reg_url";
//
//        public static final String REG_BY_ACCOUNT_URL = "reg_by_account_url";
//
//        public static final String MODIFY_ACCOUNT_INFO_URL = "modify_account_info_url";
//
//        public static final String GIFT_BAG_CDKEY_URL = "gift_bag_cdkey_url";
//
//        public static final String COMPILATIONS_URL = "compilations_url";
//
//        public static final String INFORATIONS_URL = "informations_url";
//
//        public static final String GAME_DETAIL_URL = "game_detail_url";
//
//        public static final String APP_CHECK_URL = "app_check_url";
//
//        public static final String STRATEGIES_URL = "strategies_url";
//
//        public static final String STRATEGY_DETAIL_URL = "strategy_detail_url";
//
//        public static final String MESSAGE_LIST_URL = "message_list_url";
//
//        public static final String MESSAGE_DETAIL_URL = "message_detail_url";
//
//        public static final String UPLOAD_HEAD_URL = "upload_head_url";
//
//        public static final String GAME_COMMENTS_URL = "game_comments_url";
//
//        public static final String GAME_COMMENT_AGREE_URL = "game_comment_agree_url";
//
//        public static final String CHECK_COMMENT_REPLY_URL = "check_comment_reply_url";
//
//        public static final String GAME_COMMENT_REPLY_URL = "game_comment_reply_url";
//
//        public static final String PUBLIC_STRATEGY_URL = "public_strategy_url";
//
//        public static final String PUBLIC_COMMENT_URL = "public_comment_url";
//
//        public static final String COLLECT_URL = "collect_url";
//
//        public static final String COMPILATIONS_DETAIL_URL = "compilations_detail_url";
//
//        public static final String NEW_SERVER_URL = "new_server_url";
//
//        public static final String NEW_SERVER_DETAIL_URL = "new_server_detail_url";
//
//        public static final String STATISTICS_URL = "statistics_url";
//
//        public static final String DELETE_COLLECT_URL = "delete_collect_url";
//
//        public static final String MODIFY_PSD_URL = "modify_psd_url";
//
//        public static final String BIND_EMAL_URL = "bind_email_url";
//
//        public static final String RETRIEVE_PSD_URL = "retrieve_psd_url";
//
//        public static final String RETRIEVE_PSD_CHECK_URL = "retrieve_psd_check_url";
//
//        public static final String RETRIEVE_RESET_PSD_URL = "retrieve_reset_psd_url";
//
//        public static final String BIND_PHONE_URL = "bind_phone_url";
//
//        public static final String BIND_PHONE_CHECK_URL = "bind_phone_check_url";
//
//        public static final String BIND_PHONE_MODIFY_CHECK_PSD_URL = "bind_phone_modify_check_psd_url";
//
//        public static final String PHONE_NUM_EXIST_CHECK_URL = "phone_num_exist_check_url";
//
//        public static final String PUSH_DATA_URL = "push_url";
//
//        public static final String H5_SHARE_LINK_URL = "h5_share_link_url";
//
//        public static final String SPLASH_BACKGRUAND_IMAGE_URL = "splash_backgruand_image_url";
//    }
//
//}
