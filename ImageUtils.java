class ImageUtils {
	public ImageIcon getImage(String path) {
	    URL url = getClass().getResource(path);
	    if (url != null)
	        return new ImageIcon(url);
	    return null;
	}
}