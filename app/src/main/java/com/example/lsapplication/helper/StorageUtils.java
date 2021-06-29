package com.example.lsapplication.helper;

import android.Manifest;
import android.os.Environment;

import com.example.lsapplication.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class StorageUtils
{
    public static void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }
    /**
     * Returns application assets directory. Assets directory will be created on SD card <i>("/Android/data/[app_package_name]/files/assets")</i> if card is
     * mounted and app has appropriate permission.
     *
     * @return Cache {@link File directory}
     */
    public static File getAppAssetsDir()
    {
        File appCacheDir = null;
        if (StorageUtils.hasExternalStoragePermission())
            appCacheDir = new File(StorageUtils.getAppDir(), File.separator + "assets" + File.separator);
        if (appCacheDir != null && (appCacheDir.mkdirs() || appCacheDir.isDirectory()))
            return appCacheDir;
        else
            return App.appContext.getCacheDir();
    }

    /**
     * Returns application cache directory. Cache directory will be created on SD card <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted and
     * app has appropriate permission.
     *
     * @return Cache {@link File directory}
     */
    public static File getAppCacheDir()
    {
        File appCacheDir = null;
        if (StorageUtils.hasExternalStoragePermission())
            appCacheDir = App.appContext.getExternalCacheDir();
        if (appCacheDir != null && (appCacheDir.mkdirs() || appCacheDir.isDirectory()))
            return appCacheDir;
        else
            return App.appContext.getCacheDir();
    }

    /**
     * Returns application directory. Directory will be created on SD card <i>("/Android/data/[app_package_name]/files")</i> if card is mounted and app has
     * appropriate permission.
     *
     * @return Cache {@link File directory}
     */
    public static File getAppDir()
    {
        File appCacheDir = null;
        if (StorageUtils.hasExternalStoragePermission())
            appCacheDir = App.appContext.getExternalFilesDir(null);
        if (appCacheDir != null && (appCacheDir.mkdirs() || appCacheDir.isDirectory()))
            return appCacheDir;
        else
            return App.appContext.getCacheDir();
    }

    public static File getPrivateDir()
    {
        return App.appContext.getFilesDir();
    }

    /**
     * @return the public PICTURES or DCIM directory with the app name as a sub dir.
     */
    public static File getPublicPicturesDir()
    {
        File f = null;
        if (!StorageUtils.hasExternalStoragePermission())
            return App.appContext.getCacheDir();
        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (f == null)
            f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (f == null)
            return App.appContext.getCacheDir();
        File dir = new File(f, File.separator + AndroidUtils.getAppName() + File.separator);
        if (dir.mkdirs() || dir.isDirectory())
            return dir;
        return App.appContext.getCacheDir();
    }

    /**
     * Checks if the current App can write to external storage (usually SDCard)
     *
     // * @param context
     *            AppContext
     * @return true if has WRITE_EXTERNAL_STORAGE permission and External storage is mounted, and is writable.
     */
    public static boolean hasExternalStoragePermission()
    {
       /* boolean perm = AndroidUtils.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return perm && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                && Environment.getExternalStorageDirectory().canWrite()
                && App.appContext.getExternalFilesDir(null) != null;*/
       return true;
    }

    /*    *//**
 * @return new temp file with timestamp, in application's cache dir
 *//*
    public static File newTempFile()
    {
        return StorageUtils.newTempFile(".tmp");
    }
    *//**
 * @param fileExtention
 *            the file extention ex: ".jpg"
 * @return new temp file with timestamp, in application's cache dir
 *//*
    public static File newTempFile(String fileExtention)
    {
        if (!StringUtils.startsWith(fileExtention, "."))
            fileExtention = "." + fileExtention;
        File file = new File(StorageUtils.getAppCacheDir(), "temp" + Utils.getTimestamp() + fileExtention);
        int i = 0;
        while (file.exists() && file.isFile() && i < 100)
            file = new File(StorageUtils.getAppCacheDir(), "temp" + Utils.getTimestamp() + "_" + i++ + fileExtention);
        return file;
    }
    *//**
 * Searches linearly in the specified collection for a file with the exact name and size.
 *
 // * @param file
 *            to search for
 //* @param files
 *            collection to go through
 * @return true if found in the collection
 *//*
    public static boolean searchForFile(final File file, final Collection<File> files)
    {
        if (!file.exists() || !file.isFile())
            return false;
        try
        {
            File[] array = FileUtils.convertFileCollectionToFileArray(files);
            for (File f : array)
                if (f.exists() && f.isFile())
                    if (FileUtils.contentEquals(f, file))
                        return true;
        } catch (IOException e)
        {}
        return false;
    }
     private static File getExternalAssetsDir(final Context context)
     {
     File appCacheDir = new File(StorageUtils.getExternalDir(context), File.separator + "assets" + File.separator);
     if (!appCacheDir.exists())
     if (!appCacheDir.mkdirs())
     return null;
     return appCacheDir;
     }

     private static File getExternalCacheDir(final Context context)
     {
     File appCacheDir = new File(StorageUtils.getExternalDir(context), File.separator + "cache" + File.separator);
     if (!appCacheDir.exists())
     if (!appCacheDir.mkdirs())
     return null;
     return appCacheDir;
     }
     private static File getExternalDir(final Context context)
     {
     File appCacheDir = new File(Environment.getExternalStorageDirectory(), File.separator + "Android"
     + File.separator
     + "data"
     + File.separator
     + context.getPackageName()
     + File.separator);
     if (!appCacheDir.exists())
     if (!appCacheDir.mkdirs())
     return null;
     return appCacheDir;
     }

     private static File getExternalFilesDir(final Context context)
     {
     File appCacheDir = new File(StorageUtils.getExternalDir(context), File.separator + "files" + File.separator);
     if (!appCacheDir.exists())
     if (!appCacheDir.mkdirs())
     return null;
     return appCacheDir;
     }*/
private StorageUtils()
{}
}


