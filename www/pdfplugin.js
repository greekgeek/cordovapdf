function pdfplugin() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
pdfplugin.prototype.download = function(url, filename, successCallback, errorCallback) {
  var obj = {};
  obj.url = url;
  obj.fileName = filename;
  cordova.exec(successCallback, errorCallback, 'PDFplugin', 'download', [obj]);
}

// Installation constructor that binds pdfplugin to window
filedownloader.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.filedownloader = new filedownloader();
  return window.plugins.filedownloader;
};
cordova.addConstructor(filedownloader.install);