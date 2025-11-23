package postulatum.plantum.plantum

@JsFun("""
(url) => {
  try {
    if (typeof window !== 'undefined' && window.open) {
      window.open(url, '_blank');
      return;
    }
    if (typeof globalThis !== 'undefined' && globalThis.open) {
      globalThis.open(url, '_blank');
      return;
    }
    if (typeof location !== 'undefined') {
      location.href = url;
    }
  } catch (e) {
    if (typeof location !== 'undefined') {
      location.href = url;
    }
  }
}
""")
private external fun jsOpenNewTab(url: String)

actual fun openUrl(url: String) {
    jsOpenNewTab(url)
}
