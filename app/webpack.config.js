module.exports = {
    resolve: {
        fallback: {
            "path": require.resolve("path-browserify"), // path 모듈을 path-browserify로 대체
            "fs": require.resolve("browserify-fs"), // fs 모듈을 browserify-fs로 대체
            "os": false,
            "net": false,
            "tls": false,
            "url": require.resolve("url/")
        }
    }
};