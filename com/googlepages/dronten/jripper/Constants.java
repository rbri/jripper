/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper;


/**
 * All preference values are defined here.
 */
public class Constants {
    public static final String          APP_NAME                        = "jRipper";
    public static final String          APP_COPYRIGHT                   = "Copyright 2004-2009 by dronten@gmail.com";
    public static final String          APP_HOMEPAGE                    = "http://dronten.googlepages.com/jripper";
    public static final String          APP_LICENSE                     = "This program is distributed under the terms of the GPL v3.";
    public static final String          APP_VERSION                     = "1.0";
    public static final String          APP_VERSION_FREEDB              = "jRipper+1.0";
    public static final String          CODEPAGE                        = "codepage";

    public static final int             LOG_LEVEL                       = 3;
    public static final boolean         DEBUG                           = false;

    public static final int             MP3_EXTREME_PARAM               = 0;
    public static final int             MP3_HIGH_PARAM                  = 1;
    public static final int             MP3_NORMAL_PARAM                = 2;
    public static final int             MP3_BOOK_PARAM                  = 3;
    public static final int             OGG_EXTREME_PARAM               = 4;
    public static final int             OGG_HIGH_PARAM                  = 5;
    public static final int             OGG_NORMAL_PARAM                = 6;
    public static final int             OGG_BOOK_PARAM                  = 7;
    public static final int             AAC_EXTREME_PARAM               = 8;
    public static final int             AAC_HIGH_PARAM                  = 9;
    public static final int             AAC_NORMAL_PARAM                = 10;
    public static final int             AAC_BOOK_PARAM                  = 11;
    public static final int             FLAC_PARAM                      = 12;
    public static final int             WAV_PARAM                       = 13;

    public static final int             CD_TRACK                        = 1;
    public static final int             MP3_TRACK                       = 2;
    public static final int             OGG_TRACK                       = 3;
    public static final int             M4A_TRACK                       = 4;
    public static final int             FLAC_TRACK                      = 5;
    public static final int             WAV_TRACK                       = 6;

    public static final String          AAC_ENCODER_DEFAULT             = "faac";
    public static final String          AAC_ENCODER_KEY                 = "faac_encoder";
    public static final String          AAC_DECODER_DEFAULT             = "faad";
    public static final String          AAC_DECODER_KEY                 = "faad_decoder";

    public static final boolean         ALBUM_DATA_DEFAULT              = false;
    public static final String          ALBUM_DATA_KEY                  = "allow_no_album_data";

    public static final String          BASE_DIRECTORY_KEY              = "base_directory";

    public static String                CD_DEVICE_DEFAULT               = "/dev/cdrom";
    public static final String          CD_DEVICE_KEY                   = "cd_device";

    public static final String          CD_READER_DEFAULT               = "cdda2wav";
    public static final String          CD_READER_KEY                   = "cd_reader";

    public static final boolean         CD_MONO_DEFAULT                 = false;
    public static final String          CD_MONO_KEY                     = "cd_mono";

    public static final int             CD_SPEED_DEFAULT                = 0;
    public static final String          CD_SPEED_KEY                    = "cd_speed";

    //public static final int             FREEDB_DEFAULT                  = 0;
    //public static final String          FREEDB_KEY                      = "cddb_option";

    public static final String          FREEDB_EMAIL_KEY                = "email";
    public static final String          FREEDB_EMAIL_DEFAULT            = "anonymous";
    public static final String          FREEDB_PORT_DEFAULT             = "80";
    public static final String          FREEDB_PORT_KEY                 = "cddb_port";
    public static final String          FREEDB_SERVER_DEFAULT           = "freedb.freedb.org";
    public static final String          FREEDB_SERVER_KEY               = "cddb_server";
    public static final String[]        FREEDB_OPTIONS                  = {"freedb.freedb.org", "at.freedb.org", "au.freedb.org", "ca.freedb.org", "ca2.freedb.org", "de.freedb.org", "es.freedb.org", "fi.freedb.org", "ru.freedb.org", "uk.freedb.org", "us.freedb.org"};

    public static final String          FREEDBP_PORT_DEFAULT            = "8880";
    public static final String          FREEDBP_PORT_KEY                = "cddb_p_port";
    public static final String          FREEDBP_SERVER_DEFAULT          = "freedb.freedb.org";
    public static final String          FREEDBP_SERVER_KEY              = "cddb_p_server";

    public static final boolean         CUE_SHEET_DEFAULT               = false;
    public static final String          CUE_SHEET_KEY                   = "cuesheet";

    public static final String          DIRECTORY_KEY                   = "filnamediroption";
    public static final int             DIRECTORY_DEFAULT               = 0;
    public static final String[]        DIRECTORY_OPTIONS               = {"Artist/Album", "Genre/Artist/Album", "Artist - Album", "Genre/Artist - Album", "Artist/Album (Year)", "Artist - Album (Year)"};

    public static final int             ENCODER_INDEX_MP3               = 0;
    public static final int             ENCODER_INDEX_OGG               = 4;
    public static final int             ENCODER_INDEX_AAC               = 8;
    public static final int             ENCODER_INDEX_FLAC              = 12;
    public static final int             ENCODER_INDEX_WAV               = 13;
    public static final int             ENCODER_INDEX_COUNT             = 14;

    public static final int             ENCODER_DEFAULT                 = 1;
    public static final String          ENCODER_KEY                     = "encoder_option";
    public static final String[]        ENCODER_EXTENSION_OPTIONS       = {"", "", ".mp3", ".ogg", ".m4a", ".flac", ".wav"};
    public static final String[]        ENCODER_NAME_DEFAULTS           = {"MP3 Extreme Quality", "MP3 High Quality", "MP3 Normal Quality", "MP3 Audio Book", "Ogg Extreme Quality", "Ogg High Quality", "Ogg Normal Quality", "Ogg Audio Book", "AAC Extreme Quality", "AAC High Quality", "AAC Normal Quality", "AAC Audio Book", "Flac Default Compression", "Wav"};
    public static final String[]        ENCODER_NAME_KEYS               = {"encodername0", "encodername1", "encodername2", "encodername3", "encodername4", "encodername5", "encodername6", "encodername7", "encodername8", "encodername9", "encodername10", "encodername11", "encodername12", "encodername13", "encodername14"};
    public static final String[]        ENCODER_PARAM_DEFAULTS          = {"--preset fast extreme", "--preset fast standard", "--preset cbr 128", "--vbr-new -b 32 -B 40 -m m --resample 32", "-b 256", "-b 192", "-b 128", "-b 40 --downmix --resample 32000", "-q 400", "-q 200", "-q 100", "-q 40", "-5", ""};
    public static final String[]        ENCODER_PARAM_KEYS              = {"encoderparam0", "encoderparam1", "encoderparam2", "encoderparam3", "encoderparam4", "encoderparam5", "encoderparam6", "encoderparam7", "encoderparam8", "encoderparam9", "encoderparam10", "encoderparam11", "encoderparam12", "encoderparam13", "encoderparam14"};

    public static final String          FILENAME_KEY                    = "filenameoption";
    public static final int             FILENAME_DEFAULT                = 0;
    public static final String[]        FILENAME_OPTIONS                = {"Track", "Artist - Track", "Album - Track", "Artist - Album - Track"};

    public static final int             FILENUMBER_DEFAULT              = 1;
    public static final String          FILENUMBER_KEY                  = "filenumber_option";
    public static final String[]        FILENUMBER_OPTIONS              = {"1", "01", "001"};

    public static final String          FILENUMBER_SEPERATOR_DEFAULT    = " ";
    public static final String          FILENUMBER_SEPERATOR_KEY        = "filenumber_sep";

    public static final boolean         FILENUMBER_USE_DEFAULT          = true;
    public static final String          FILENUMBER_USE_KEY              = "usefilenumber";

    public static final String          FLAC_ENCODER_DEFAULT            = "flac";
    public static final String          FLAC_ENCODER_KEY                = "flac_encoder";

    public static final String          MP3_ENCODER_DEFAULT             = "lame";
    public static final String          MP3_ENCODER_KEY                 = "lame_encoder";

    public static final String          OGG_ENCODER_DEFAULT             = "oggenc";
    public static final String          OGG_ENCODER_KEY                 = "ogg_encoder";
    public static final String          OGG_DECODER_DEFAULT             = "oggdec";
    public static final String          OGG_DECODER_KEY                 = "ogg_decoder";

    public static final String          PARANOIA_KEY                    = "cd_use_paranoia";
    public static final boolean         PARANOIA_DEFAULT                = false;

    public static final String          PREFERENCE_NAME                 = "jRipper";
    public static final String          PREFERENCE_NAME_DEBUG           = "jRipperDebug";

    public static final String[]        SETUP_DEVICE_OPTIONS            = {"--- Common Devices ---", "1,0,0", "2,0,0", "/dev/cdrom", "/dev/hdb", "/dev/hdc"};
    public static final String[]        SETUP_DIALOG_TABS               = {"General Options  ", "Music File Names    ", "Title Tag Options", "Program Paths  ", "FreeDB Settings  ", "CD Reader Settings   ", "Encoder Parameters  "};

    public static int                   TITLE_DEFAULT                   = 0;
    public static String                TITLE_KEY                       = "title_tag";
    public static final String[]        TITLE_OPTIONS                   = {"Title", "Artist - Title", "Artist / Title", "Artist - Album - Title", "Artist / Album / Title", "Album - Title", "Album / Title"};

    public static boolean               TITLE_REMOVEPREFIX_DEFAULT      = true;
    public static String                TITLE_REMOVEPREFIX_KEY          = "remove_prefix";

    public enum DataFormat {
                                        WAV,
                                        PCM,
    }
}
