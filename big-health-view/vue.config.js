// module.exports = {
//   lintOnSave: false,
//   devServer: {
//     host: "localhost",
//     port: 21091,
//     https: false,
//     proxy: "http://localhost:21090",
//     overlay: {
//       warning: false,
//       errors: false
//     }
//   }
// };
module.exports = {
  lintOnSave: false,
  devServer: {
    host: "localhost",
    port: 21091,
    https: false,
    proxy: {
      // 匹配以 /api 开头的请求
      "/api": {
        target: "http://localhost:21090",
        changeOrigin: true,
        pathRewrite: {
          "^/api": "/api"
        }
      }
    },
    overlay: {
      warning: false,
      errors: false
    }
  }
};
