# ego 商城项目

#项目介绍
ego-parent：maven 父项目，存放子项目需要的artifact坐标，用于子项目依赖
ego-commons: 工具类,存放其他项目用到的工具类
ego-pojo：实体类项目，存放ORM数据库映射对应的实体类
ego-service：接口项目，存放接口
ego-service-impl：服务项目，存放mapper与service实现类，用于对数据库的访问与操作，这是一个dubbo provider,用于向其他对象提供代理对象用于对数据库操作
ego-manage:后台项目，用于实现后台功能
ego-portal:前台项目，用于实现前台功能
ego-search:商品搜索功能，使用到了solr框架
ego-passport:用户身份验证的项目
ego-cart:购物车功能项目
ego-order:下单项目

#功能

dubbo:ego-service-impl项目即作为dubbo的provider，配置好注册中心，dubbo协议，同时在配置文件中注册好功能，之后Main.main运行，或是Assembly打包jar包运行，在注册中心注册，
即可以通过其他项目（消费者）远程调用此项目的功能，此时传输给消费者一个代理对象进行远程调用，这个过程是同步执行的。

跨域：在实现前台的菜单功能时，用到jsonp,从portal项目发送请求到item项目获取菜单数据，jsonp的实现原理为，ajax不允许跨域请求，
但是却可以请求另一项目的js文件，因此，前后端约定好回调函数名，通过ajax请求服务端返回JavaScript，将数据放在回调函数参数中传递。

redis缓存：前台广告位（url）数据缓存，由于不可能每次用户访问，都去数据库查询前台广告位的数据，因此避免数据库压力过大，使用redis缓存，此处使用了redis集群以及连接池，
和其他池类技术一样，频繁生成销毁连接对象会消耗大量性能，因此，采用连接池技术节约性能，redis缓存思路为首先去redis查询key对应的value，如果有值，则返回，如果为空，继续去数据库查，
查到的结果存入redis缓存中，然后返回。

solr搜索:solr实际上是一个war项目，提供特定的API叫solrJ与solr进行交互，solr存储了需要搜索的数据，具备数据持久化功能，solr是基于索引进行查询的，提供了顺序查询和反向键索引
，solr对中文拆词的功能支持不好，可以使用IK Analyzer 拆词器

跨域2：使用Httpclient模拟浏览器发起请求，并获得返回数据，使用场景，在后台（ego-manage）添加商品时，同时发起请求到ego-search,把新增商品持久化到solr中

SSO单点登录：一次登录,让其他项目共享登录状态。如果不是分布式的项目，那么直接HttpSession就可以做到，session是服务器存储的内存空间，但是分布式情况下，项目分布在不同的服务器里，
因此，需要用到Redis+Cookie来模拟session,进行单点登录的实现，实现思路：用户提交验证，通过查询数据库验证登录后，存储登录信息到redis中，并把key返回给浏览器，这样当浏览器需要登录的情况下
如加入购物车或者收藏时（这是访问别的项目），即可以通过Ajax携带cookie发送请求到另一项目，这个项目获取到cookie作为key查询到redis中用户登录情况，进行登录验证，返回用户信息。
注销只需删除cookie以及redis中的登录信息即可。





