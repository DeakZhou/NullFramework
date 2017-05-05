package com.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.text.format.Time;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {
	private static final int DEFAULT_REQUIRED_SIZE = 70;
	/*public static Bitmap decodeFile(File f, int size) {
		try {
			BitmapFactory.Options option = new BitmapFactory.Options();
			option.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(f.getPath(), option);
			int scale = getInSampleSize(size, option.outWidth, option.outHeight);
			BitmapFactory.Options option2 = new BitmapFactory.Options();
			option2.inSampleSize = scale;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, option2);
			stream2.close();
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	public static Bitmap decodeFile(File f, int size) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 通过这个bitmap获取图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(f.getPath(), options);
		if (bitmap == null) {
			//System.out.println("bitmap为空");
		}
		float realWidth = options.outWidth;
		float realHeight = options.outHeight;
		//System.out.println("真实图片高度：" + realHeight + "宽度:" + realWidth);
		// 计算缩放比
		int scale = getInSampleSize(size, options.outWidth, options.outHeight);
		options.inSampleSize = scale;
		options.inJustDecodeBounds = false;
		// 注意这次要把options.inJustDecodeBounds 设为 false,这次图片是要读取出来的。
		bitmap = BitmapFactory.decodeFile(f.getPath(), options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		return bitmap;
	}

	/**
	 * 以最省内存的方式读取本地资源的图片
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		//获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is,null,opt);
	}

	private static int getInSampleSize(int size, int width, int height){
		final int REQUIRED_SIZE = size > 0 ? size : DEFAULT_REQUIRED_SIZE;
		int width_tmp = width;
		int height_tmp = height;
		while (true) {
			if (width / 2 < REQUIRED_SIZE
					|| height / 2 < REQUIRED_SIZE)
				break;
			width /= 2;
			height /= 2;
		}
		int scale = (width_tmp > height_tmp ? width_tmp : height_tmp) / (width > height ? width : height);
		if(scale <= 0){
			scale = 1;
		}
		return scale;
	}

	//生成圆角图片
	public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap, int round) {
		try {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight()));
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.BLACK);
			canvas.drawRoundRect(rectF, round, round, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

			final Rect src = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());

			canvas.drawBitmap(bitmap, src, rect, paint);
			return output;
		} catch (Exception e) {
			return bitmap;
		}
	}

	public static Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle(); // Bitmap操作完显示释放
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				return b;
			}
		}
		return b;
	}

	/**
	 * 图片旋转
	 *
	 * @param angle
	 * @param bitmap
	 * @return
	 */
	public static Bitmap rotaingImageView(Bitmap bitmap, int angle) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		System.out.println("angle=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	/**
	 * Bitmap transfer to bytes
	 *
	 * @param bm
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bm) {
		byte[] bytes = null;
		if (bm != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			bytes = baos.toByteArray();
		}
		return bytes;
	}
	public static Bitmap scale(Bitmap bm, float screenWidth, float screenHeight) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();

		float _Scale = 1.0f; //定义缩放因子,默认值为1.0表示图片的大小和控件的大小刚好一样大

		if(width > screenWidth){
			if(height > screenHeight){
				_Scale = Math.max(screenWidth * 1.0f / width, screenHeight * 1.0f / height);
			} else if(height < screenHeight){
				_Scale = screenHeight * 1.0f / height;
			}
		} else if(width < screenWidth){
			if(height >= screenHeight){
				_Scale = screenWidth * 1.0f / width;
			} else if(height < screenHeight){
				_Scale = Math.max(screenWidth * 1.0f / width, screenHeight * 1.0f / height);
			}
		} else {
			if(height < screenHeight){
				_Scale = screenHeight * 1.0f / height;
			}
		}
		// 取得想要缩放的matrix参数
		float _ScaleWidth;
		float _ScaleHeight;
		Bitmap scaledBitmap;
		if(_Scale != 1.0){
			_ScaleWidth =  width*_Scale;
			_ScaleHeight = height*_Scale;
			scaledBitmap = Bitmap.createScaledBitmap(bm, ((int)_ScaleWidth)>0 ? (int)_ScaleWidth : (int)screenWidth,  ((int)_ScaleHeight)>0 ? (int)_ScaleHeight : (int)screenHeight, true);
		} else {
			scaledBitmap = bm;
		}
		//从图中截取正中间的部分。
		int xTopLeft = (int) ((scaledBitmap.getWidth() - screenWidth) / 2);
		int yTopLeft = (int) ((scaledBitmap.getHeight() - screenHeight) / 2);

		scaledBitmap = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft,(int) screenWidth, (int) screenHeight);

		return scaledBitmap;
	}

	/*public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
		Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
		Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(scaledBitmap);
		canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));return scaledBitmap;
	}

	public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		if (scalingLogic == ScalingLogic.CROP) {
			final float srcAspect = (float)srcWidth / (float)srcHeight;
			final float dstAspect = (float)dstWidth / (float)dstHeight;
			if (srcAspect > dstAspect) {
				final int srcRectWidth = (int)(srcHeight * dstAspect);
				final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
				return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
			} else {
				final int srcRectHeight = (int)(srcWidth / dstAspect);
				final int scrRectTop = (int)(srcHeight - srcRectHeight) / 2;
				return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
			}
		} else {
			return new Rect(0, 0, srcWidth, srcHeight);
		}
	}
	public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		if (scalingLogic == ScalingLogic.FIT) {
			final float srcAspect = (float)srcWidth / (float)srcHeight;
			final float dstAspect = (float)dstWidth / (float)dstHeight;
			if (srcAspect > dstAspect) {
				return new Rect(0, 0, dstWidth, (int)(dstWidth / srcAspect));
			} else {
				return new Rect(0, 0, (int)(dstHeight * srcAspect), dstHeight);
			}
		} else {
			return new Rect(0, 0, dstWidth, dstHeight);
		}
	}*/

	public static Bitmap scale(Bitmap bm, float scale) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		// 得到新的图片
		return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	}
	public static Matrix scaleMatrix(Bitmap bm, float newHeight,
									 float newWidth, float dx, float dy) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		matrix.postTranslate(dx, dy);
		// 得到新的图片
		return matrix;
	}

	// Bitmap转换成Drawable
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd;
		return d;
	}

	// Bitmap转换成Drawable
	public static Bitmap Drawable2bitmap(Drawable drawable) {
		return ((BitmapDrawable) drawable).getBitmap();
	}

	// 将Bitmap转换成InputStream
	public InputStream Bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}
	// 将Bitmap转换成InputStream
	public InputStream Bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}
	// private BitmapDrawable generatorIconWithNumber() {
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// //取出要显示的图标
	// Bitmap icon =
	// BitmapFactory.decodeResource(getResources(),R.drawable.notice, options);
	// //定义画布大小
	// Bitmap numIcon = Bitmap.createBitmap(options.outWidth + 10,
	// options.outHeight + 10, Config.ARGB_8888);
	// Canvas canvas = new Canvas(numIcon);
	// //定义画笔
	// Paint rectPaint = new Paint();
	// rectPaint.setDither(true);
	// rectPaint.setFilterBitmap(true);
	// Rect dst = new Rect(0, 0, options.outWidth, options.outHeight);
	// //画图标
	// canvas.drawBitmap(icon, null, dst, rectPaint);
	// //得到未读通知数目
	// int count = BaseService.getUnreadedNoticeNumber();
	// //画底层红色圆圈
	// rectPaint.setColor(Color.RED);
	// canvas.drawRect(options.outWidth - 15, 0, options.outWidth, 15,
	// rectPaint);
	// //画上层白色数字
	// Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
	// | Paint.DEV_KERN_TEXT_FLAG);
	// countPaint.setColor(Color.WHITE);
	// countPaint.setTextSize(15f);
	// countPaint.setTypeface(Typeface.DEFAULT_BOLD);
	// canvas.drawText(String.valueOf(count), options.outWidth - 12, 12,
	// countPaint);
	//
	// BitmapDrawable bd = new BitmapDrawable(getResources(), numIcon);
	// return bd;
	// }
	/**
	 * 获取圆形图片
	 *
	 * @param bitmap
	 * @return
	 */
	public static Bitmap toCycleBitmap(Bitmap bitmap) {
		try {
			int strokeWidth = 4;
			int x = bitmap.getWidth();
			int y = bitmap.getHeight();
			int radius;
			if (x > y) {
				radius = y;
			} else {
				radius = x;
			}
			Bitmap output = Bitmap.createBitmap(x + strokeWidth, y
					+ strokeWidth, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(output);

			final int color = Color.WHITE;
			final Paint paint = new Paint();
			// 根据原来图片大小画一个矩形
			final Rect rect = new Rect(strokeWidth / 2, strokeWidth / 2, x, y);
			paint.setAntiAlias(true);
			paint.setColor(color);
			// 画出一个圆
			canvas.drawCircle((x + strokeWidth) / 2, (strokeWidth + y) / 2,
					radius / 2, paint);
			// 取两层绘制交集,显示上层
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			// 将图片画上去
			canvas.drawBitmap(bitmap, rect, rect, paint);
			Paint p = new Paint();
			p.setAntiAlias(true);
			p.setColor(Color.WHITE);
			p.setStrokeWidth(strokeWidth);
			p.setStyle(Paint.Style.STROKE);
			canvas.drawCircle((x + strokeWidth) / 2, (strokeWidth + y) / 2,
					radius / 2, p);
			return output;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *
	 * Date:2014-8-28
	 * Description: 图片质量压缩
	 * @param image
	 * @param compressSize
	 * @return
	 * @return Bitmap
	 */
	public static void compressImage(Bitmap image, int compressSize, String path) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		while ( baos.toByteArray().length / 1024>compressSize) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.PNG, compressSize, baos);//这里压缩options%，把压缩后的数据存放到baos中
			compressSize -= 10;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		saveBitmapFile(bitmap, path);
	}

	/**
	 * 根据路径获取图片资源（已缩放）
	 * @param url 图片存储路径
	 * @param width 缩放的宽度
	 * @param height 缩放的高度
	 * @return
	 */
	public static Bitmap getBitmapFromUrl(String url, double width, double height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
		Bitmap bitmap = BitmapFactory.decodeFile(url);
		// 防止OOM发生
		options.inJustDecodeBounds = false;
		int mWidth = bitmap.getWidth();
		int mHeight = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = 1;
		float scaleHeight = 1;
//        try {
//            ExifInterface exif = new ExifInterface(url);
//            String model = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		// 按照固定宽高进行缩放
		// 这里希望知道照片是横屏拍摄还是竖屏拍摄
		// 因为两种方式宽高不同，缩放效果就会不同
		// 这里用了比较笨的方式
		if(mWidth <= mHeight) {
			scaleWidth = (float) (width/mWidth);
			scaleHeight = (float) (height/mHeight);
		} else {
			scaleWidth = (float) (height/mWidth);
			scaleHeight = (float) (width/mHeight);
		}
//        matrix.postRotate(90); /* 翻转90度 */
		// 按照固定大小对图片进行缩放
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight, matrix, true);
		// 用完了记得回收
		bitmap.recycle();
		return newBitmap;
	}

	public static void saveBitmapFile(Bitmap bitmap, String path){
		FileUtil.deleteFile(path);
		// 文件夹路径
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
											int reqWidth, int reqHeight) {

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
		}

		return inSampleSize;
	}
	public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static Bitmap byteToBitmap(byte[] imgByte, int size) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);
		options.inSampleSize = size <= 0 ? 1 : getInSampleSize(size, options.outWidth, options.outHeight);;
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);
		return bitmap;
	}

	/**
	 * 获取图片信息
	 *
	 * @param path
	 * @return
	 */public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;

	}

	public static Bitmap convertBitmap(File file) throws IOException {
		Bitmap bitmap = null;
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		BitmapFactory.decodeStream(fis, null, o);
		fis.close();
		final int REQUIRED_SIZE = 70;
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inSampleSize = scale;
		fis = new FileInputStream(file.getAbsolutePath());
		bitmap = BitmapFactory.decodeStream(fis, null, op);
		fis.close();
		return bitmap;
	}

	public static Bitmap createWatermarkBitmap(Bitmap src, String watermark, int x, int y) {
		Time t = new Time();
		t.setToNow();
		int w = src.getWidth();
		int h = src.getHeight();
		Bitmap bmpTemp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(bmpTemp);
		Paint p = new Paint();
		String familyName = "宋体";
		Typeface font = Typeface.create(familyName, Typeface.BOLD);
		p.setColor(Color.YELLOW);
		p.setTypeface(font);
		p.setTextSize(22);
		canvas.drawBitmap(src, 0, 0, p);
		canvas.drawText(watermark, x, y, p);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return bmpTemp;
	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图
	 * 此方法有两点好处：
	 *     1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 *        第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
	 *     2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
	 *        用这个工具生成的图像不会被拉伸。
	 * @param imagePath 图像的路径
	 * @param width 指定输出图像的宽度
	 * @param height 指定输出图像的高度
	 * @return 生成的缩略图
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	public static Bitmap getImageThumbnail(Context context, Uri uri, int size){
		Bitmap bitmap = null;
		try{
			InputStream input = context.getContentResolver().openInputStream(uri);
			BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
			onlyBoundsOptions.inJustDecodeBounds = true;
			onlyBoundsOptions.inDither=true;//optional
			onlyBoundsOptions.inPreferredConfig= Bitmap.Config.ARGB_8888;//optional
			BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
			input.close();
			if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
				return null;
			int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
			double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
			bitmapOptions.inDither=true;//optional
			bitmapOptions.inPreferredConfig= Bitmap.Config.ARGB_8888;//optional
			input = context.getContentResolver().openInputStream(uri);
			bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
			input.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return bitmap;
	}
	private static int getPowerOfTwoForSampleRatio(double ratio){
		int k = Integer.highestOneBit((int) Math.floor(ratio));
		if(k==0) return 1;
		else return k;
	}

	public static void recycleBitmap(Bitmap b){
		if(null != b && b.isRecycled()){
			b.recycle();
			b = null;
			System.gc();
		}
	}


	public static void imageZoom(Bitmap bitMap, double maxSize) {
		//图片允许最大空间   单位：KB
		//将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		//将字节换成KB
		double mid = b.length/1024;
		//判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			//获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			//开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
					bitMap.getHeight() / Math.sqrt(i));
		}
	}



	/***
	 * 图片的缩放方法
	 *
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
								   double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}
}
