function filedownloader() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
filedownloader.prototype.download = function(param, successCallback, errorCallback) {
  var obj = {};
  if ((typeof param) === (typeof obj)) {
    obj.filename = param.filename;
    obj.fileurl = param.fileurl;
    obj.folder = param.folder;
  }
  cordova.exec(successCallback, errorCallback, 'FileDownloader', 'download', [obj]);
}

// Installation constructor that binds filedownloader to window
filedownloader.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.filedownloader = new filedownloader();
  return window.plugins.filedownloader;
};
cordova.addConstructor(filedownloader.install);