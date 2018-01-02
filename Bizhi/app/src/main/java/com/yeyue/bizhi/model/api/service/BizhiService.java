package com.yeyue.bizhi.model.api.service;

import com.yeyue.bizhi.entity.AlbumBean;
import com.yeyue.bizhi.entity.AlbumDetail;
import com.yeyue.bizhi.entity.BizhiBean;
import com.yeyue.bizhi.entity.GankBean;
import com.yeyue.bizhi.entity.HomePage;
import com.yeyue.bizhi.entity.HotSearch;
import com.yeyue.bizhi.entity.HttpAlbum;
import com.yeyue.bizhi.entity.HttpAlbumRequest;
import com.yeyue.bizhi.entity.HttpCategory;
import com.yeyue.bizhi.entity.HttpSkins;
import com.yeyue.bizhi.entity.HttpVertical;
import com.yeyue.bizhi.entity.HttpVideo;
import com.yeyue.bizhi.entity.HttpWallpaper;
import com.yeyue.bizhi.entity.RecommendBean;
import com.yeyue.bizhi.entity.SearchAll;
import com.yeyue.bizhi.entity.SearchTags;
import com.yeyue.bizhi.entity.SkinBean;
import com.yeyue.bizhi.entity.UserDetail;
import com.yeyue.bizhi.entity.VerCategory;
import com.yeyue.bizhi.entity.VideoBean;
import com.yeyue.bizhi.entity.VideoCategory;
import com.yeyue.bizhi.entity.WalCategory;
import com.yeyue.bizhi.entity.WalDetail;
import com.yeyue.bizhi.entity.WalRecommend;
import com.yeyue.bizhi.model.api.HttpRequest;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 存放关于用户的一些api
 * Created by jess on 8/5/16 12:05
 * contact with jess.yan.effort@gmail.com
 */
public interface BizhiService {

    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";


    /**
     * 获取首页推荐数据
     * http://service.picasso.adesk.com/v3/homepage?limit=30&first=2&order=hot
     * @param limit
     * @param skip 从多少开始
     * @param order
     * @return
     */
    @GET("/v3/homepage")
    Observable<HomePage> getHomepage(@Query("limit") int limit, @Query("skip") int skip, @Query("order") String order);

    /**
     * 获取首页壁纸分类
     * http://service.picasso.adesk.com/v1/wallpaper/category
     * @return
     */
    @GET("/v1/wallpaper/category")
    Observable<HttpRequest<WalCategory>> getWalCategory();

    /**
     * 壁纸分类详情列表
     * http://service.picasso.adesk.com/v1/wallpaper/category/4e4d610cdf714d2966000000/wallpaper?limit=30&skip=2&order=new
     * @return
     */
    @GET("v1/wallpaper/category/{cId}/wallpaper")
    Observable<HttpWallpaper<BizhiBean>> getWalCategoryList(@Path("cId") String cid,@Query("limit") int limit, @Query("skip") int skip, @Query("order") String order);

    /**
     * 壁纸分类专辑列表
     * http://service.picasso.adesk.com/v1/wallpaper/category/4e4d610cdf714d2966000000/album?limit=10&adult=false&first=1&order=new
     * @return
     */
    @GET("v1/wallpaper/category/{cId}/album")
    Observable<HttpAlbumRequest<AlbumBean>> getWalCategoryAlbum(@Path("cId") String cid, @Query("limit") int limit, @Query("skip") int skip, @Query("order") String order);

    /**
     * 获取首页壁纸最新
     * http://service.picasso.adesk.com/v1/wallpaper/wallpaper?limit=30&first=40&order=new
     * @return
     */
    @GET("/v1/wallpaper/wallpaper")
    Observable<HttpWallpaper<BizhiBean>> getWalNew(@Query("limit") int limit,@Query("skip") int skip,@Query("order") String order);

    /**
     * 获取首页壁纸详情
     * http://service.picasso.adesk.com/v2/wallpaper/wallpaper/59f3082ae7bce729d564ff6f/comment
     * @return
     */
    @GET("v2/wallpaper/wallpaper/{Id}/comment")
    Observable<HttpRequest<WalDetail>> getWalDetail(@Path("Id") String id);


    /**
     * 获取竖屏壁纸数据
     * http://service.picasso.adesk.com/v1/vertical/vertical?limit=30&first=1&order=hot
     * @return
     */
    @GET("/v1/vertical/vertical")
    Observable<HttpVertical<BizhiBean>> getVerticalBizhi(@Query("limit") int limit,@Query("skip") int skip,@Query("order") String order);

    /**
     * 获取竖屏壁纸分类
     * http://service.picasso.adesk.com/v1/vertical/category
     * @return
     */
    @GET("/v1/vertical/category")
    Observable<HttpCategory<VerCategory>> getVerCategory();

    /**
     * 竖屏分类详情列表
     * http://service.picasso.adesk.com/v1/vertical/category/4e4d610cdf714d2966000000/vertical?limit=30&adult=false&first=1&order=new
     * @return
     */
    @GET("v1/vertical/category/{cId}/vertical")
    Observable<HttpVertical<BizhiBean>> getVerCategoryList(@Path("cId") String cid,@Query("limit") int limit, @Query("skip") int skip, @Query("order") String order);

    /**
     * 获取首页壁纸专辑
     * http://service.picasso.adesk.com/v1/wallpaper/album?limit=10&first=1&order=new
     * @return
     */
    @GET("/v1/wallpaper/album")
    Observable<HttpAlbum> getAlbum(@Query("limit") int limit,@Query("skip") int skip,@Query("order") String order);

    /**
     * 获取专辑详情页
     * http://service.picasso.adesk.com/v1/wallpaper/album/5a24cd3ae7bce7253c78ae3f/wallpaper?limit=30&adult=false&first=1&order=new
     * @return
     */
    @Headers("User-Agent: picasso,186,xiaomi")
    @GET("v1/wallpaper/album/{albumId}/wallpaper")
    Observable<HttpRequest<AlbumDetail>> getAlbumDetail(@Path("albumId") String albumId, @Query("limit") int limit, @Query("first") int first, @Query("order") String order);

    /**
     * 获取皮肤
     * http://vip1.kuwo.cn/vip/v2/theme?op=gat&platform=ar&id=&appVersion=8573&versions=V4
     * @return
     */
    @GET("http://111.230.154.222/skins/kwjson")
    Observable<HttpSkins> getSkinList();



    /**
     * 每日精选
     * http://service.picasso.adesk.com/v1/wallpaper/recommend?limit=6&adult=false&first=1
     * @return
     */
    @GET("/v1/wallpaper/recommend")
    Observable<WalRecommend> getWalRecommend(@Query("limit") int limit,@Query("skip") int skip,@Query("first") int first);

    /**
     * 视频推荐
     * https://service.videowp.adesk.com/v1/videowp/featured?limit=30&adult=false&first=1&order=hot
     * @return
     */
    @GET("https://service.videowp.adesk.com/v1/videowp/{type}")
    Observable<HttpVideo<VideoBean>> getVideoList(@Path("type") String type,@Query("limit") int limit, @Query("skip") int skip, @Query("order") String order);

    /**
     * 视频分类详情
     * https://service.videowp.adesk.com/v1/videowp/category/5930e065e7bce72ce01371b1?limit=30&adult=false&first=1&order=new
     * @param limit
     * @param skip
     * @param order
     * @return
     */
    @GET("https://service.videowp.adesk.com/v1/videowp/category/{cate_id}")
    Observable<HttpVideo<VideoBean>> getVideoCateGoryDetail(@Path("cate_id") String cate_id,@Query("limit") int limit, @Query("skip") int skip, @Query("order") String order);


    /**
     * 获取视频壁纸分类
     * https://service.videowp.adesk.com/v1/videowp/category
     * @return
     */
    @GET("https://service.videowp.adesk.com/v1/videowp/category")
    Observable<HttpCategory<VideoCategory>> getVideoCategory();


    /**
     * 获取用户信息
     * http://service.picasso.adesk.com/v1/user/5965cd0be7bce7312ef79fbf
     * @return
     */
    @GET("v1/user/{uId}")
    Observable<HttpRequest<UserDetail>> getUserDetail(@Path("uId") String uId);

    /**
     * 用户横屏壁纸
     * http://service.picasso.adesk.com/v1/user/5965cd0be7bce7312ef79fbf/wallpaper?action=upload&limit=30&adult=false&first=1&order=new
     */
    @GET("/v1/user/{uId}/wallpaper")
    Observable<HttpWallpaper<BizhiBean>> getUserWallPaper(@Path("uId") String uId,@Query("limit") int limit, @Query("skip") int skip, @Query("order") String order,@Query("action") String action);
    /**
     * 用户竖屏壁纸
     * http://service.picasso.adesk.com/v1/user/5965cd0be7bce7312ef79fbf/vertical?action=upload&limit=30&adult=false&first=1&order=new
     */
    @GET("/v1/user/{uId}/vertical")
    Observable<HttpVertical<BizhiBean>> getUserVertical(@Path("uId") String uId,@Query("limit") int limit, @Query("skip") int skip, @Query("order") String order,@Query("action") String action);
    /**
     * 用户专辑
     * http://service.picasso.adesk.com/v1/user/5965cd0be7bce7312ef79fbf/album?uid=&limit=30&adult=false&first=1&order=new
     */
    @GET("/v1/user/{uId}/album")
    Observable<HttpAlbumRequest<AlbumBean>> getUserAlbum(@Path("uId") String uId,@Query("limit") int limit, @Query("skip") int skip, @Query("order") String order);



    /**
     * 获取热门搜索
     * http://service.picasso.adesk.com/v1/push/keyword?first=0&adult=false
     * @return
     */
    @GET("/v1/push/keyword")
    Observable<HttpRequest<HotSearch>> getSearchKeyword(@Query("first") int first);
    /**
     * 搜索-全部
     * http://so.picasso.adesk.com/v1/search/all/resource/%E7%BE%8E%E5%A5%B3?limit=30&first=1
     */
    @GET("http://so.picasso.adesk.com/v1/search/all/resource/{Query}")
    Observable<HttpRequest<SearchAll>> getSearchAll(@Path("Query") String Query, @Query("limit") int limit, @Query("skip") int skip);

    /**
     * 搜索-分类
     * http://so.picasso.adesk.com/v1/search/all/resource/%E7%BE%8E%E5%A5%B3?limit=30&first=1
     */
    @GET("http://so.picasso.adesk.com/v1/search/{tag}/resource/{Query}")
    Observable<HttpRequest<SearchTags>> getSearchTag(@Path("tag") String tag, @Path("Query") String Query, @Query("limit") int limit, @Query("skip") int skip);


    /**
     * 获取干货数据
     * @param type 类型 Android ,IOS,福利等等
     * @param limint 数量
     * @param Page 页码
     * @return
     */
    @GET("/api/data/{type}/{limint}/{Page}")
    Observable<HttpRequest<List<GankBean>>> getListNew(@Path("type") String type, @Path("limint") int limint, @Path("Page") int Page);

    /**
     * 搜索
     * @param query
     * @param limint
     * @param Page
     * @return
     */
    @GET("/api/search/query/{query}/category/all/count/{limint}/page/{page}")
    Observable<HttpRequest<List<GankBean>>> searchQuery(@Path("query") String query,@Path("limint") int limint,@Path("Page") int Page);



    /**
     * 获取干货推荐
     * @param limint
     * @param Page
     * @return
     */
    @GET("/api/history/content/{limint}/{page}")
    Observable<HttpRequest<List<RecommendBean>>> getHistory(@Path("limint") int limint, @Path("Page") int Page);

    /**
     * 根据时间获取推荐干货
     * @param time 2016/5/11
     * @return
     */
    @GET("/api/history/content/day/{time}")
    Observable<HttpRequest<List<RecommendBean>>> getHistoryByDay(@Path("time") String time);

    @GET("/api/day/history ")
    Observable<HttpRequest<List<String>>> getHistoryTime();

    /**
     * 获取每日数据
     * @param time
     * @return
     */
    @GET("/api/day/{time}")
    Observable<SkinBean> getDayContent(@Path("time") String time);

    /**
     * 获取干货随机数据
     * @param type 类型 Android ,IOS,福利等等
     * @param limint 数量
     * @param Page 页码
     * @return
     */
    @GET("/api/random/data/{type}/{limint}/{Page}")
    Observable<HttpRequest<List<GankBean>>> getRandom(@Path("type") String type,@Path("limint") int limint,@Path("Page") int Page);

}
