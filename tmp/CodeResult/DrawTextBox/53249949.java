/*
Copyright (C) 1996-1997 Id Software, Inc.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  

See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

*/
package quake;

#ifdef _WIN32
#include "winquake.h"
#endif

void (*vid_menudrawfn)();
void (*vid_menukeyfn)(int key);

enum {m_none, m_main, m_singleplayer, m_load, m_save, m_multiplayer, m_setup, m_net, m_options, m_video, m_keys, m_help, m_quit, m_serialconfig, m_modemconfig, m_lanconfig, m_gameoptions, m_search, m_slist} m_state;

void M.Menu_Main_f ();
	void M.Menu_SinglePlayer_f ();
		void M.Menu_Load_f ();
		void M.Menu_Save_f ();
	void M.Menu_MultiPlayer_f ();
		void M.Menu_Setup_f ();
		void M.Menu_Net_f ();
	void M.Menu_Options_f ();
		void M.Menu_Keys_f ();
		void M.Menu_Video_f ();
	void M.Menu_Help_f ();
	void M.Menu_Quit_f ();
void M.Menu_SerialConfig_f ();
	void M.Menu_ModemConfig_f ();
void M.Menu_LanConfig_f ();
void M.Menu_GameOptions_f ();
void M.Menu_Search_f ();
void M.Menu_ServerList_f ();

void M.Main_Draw ();
	void M.SinglePlayer_Draw ();
		void M.Load_Draw ();
		void M.Save_Draw ();
	void M.MultiPlayer_Draw ();
		void M.Setup_Draw ();
		void M.Net_Draw ();
	void M.Options_Draw ();
		void M.Keys_Draw ();
		void M.Video_Draw ();
	void M.Help_Draw ();
	void M.Quit_Draw ();
void M.SerialConfig_Draw ();
	void M.ModemConfig_Draw ();
void M.LanConfig_Draw ();
void M.GameOptions_Draw ();
void M.Search_Draw ();
void M.ServerList_Draw ();

void M.Main_Key (int key);
	void M.SinglePlayer_Key (int key);
		void M.Load_Key (int key);
		void M.Save_Key (int key);
	void M.MultiPlayer_Key (int key);
		void M.Setup_Key (int key);
		void M.Net_Key (int key);
	void M.Options_Key (int key);
		void M.Keys_Key (int key);
		void M.Video_Key (int key);
	void M.Help_Key (int key);
	void M.Quit_Key (int key);
void M.SerialConfig_Key (int key);
	void M.ModemConfig_Key (int key);
void M.LanConfig_Key (int key);
void M.GameOptions_Key (int key);
void M.Search_Key (int key);
void M.ServerList_Key (int key);

boolean	m_entersound;		// play after drawing a frame, so caching
								// won't disrupt the sound
boolean	m_recursiveDraw;

int			m_return_state;
boolean	m_return_onerror;
char		m_return_reason [32];

#define StartingGame	(m_multiplayer_cursor == 1)
#define JoiningGame		(m_multiplayer_cursor == 0)
#define SerialConfig	(m_net_cursor == 0)
#define DirectConfig	(m_net_cursor == 1)
#define	IPXConfig		(m_net_cursor == 2)
#define	TCPIPConfig		(m_net_cursor == 3)

void M.ConfigureNetSubsystem();

/*
================
M.DrawCharacter

Draws one solid graphics character
================
*/
void M.DrawCharacter (int cx, int line, int num)
{
	Draw.Character ( cx + ((vid.width - 320)>>1), line, num);
}

void M.Print (int cx, int cy, char *str)
{
	while (*str)
	{
		M.DrawCharacter (cx, cy, (*str)+128);
		str++;
		cx += 8;
	}
}

void M.PrintWhite (int cx, int cy, char *str)
{
	while (*str)
	{
		M.DrawCharacter (cx, cy, *str);
		str++;
		cx += 8;
	}
}

void M.DrawTransPic (int x, int y, qpic_t *pic)
{
	Draw.TransPic (x + ((vid.width - 320)>>1), y, pic);
}

void M.DrawPic (int x, int y, qpic_t *pic)
{
	Draw.Pic (x + ((vid.width - 320)>>1), y, pic);
}

byte identityTable[256];
byte translationTable[256];

void M.BuildTranslationTable(int top, int bottom)
{
	int		j;
	byte	*dest, *source;

	for (j = 0; j < 256; j++)
		identityTable[j] = j;
	dest = translationTable;
	source = identityTable;
	memcpy (dest, source, 256);

	if (top < 128)	// the artists made some backwards ranges.  sigh.
		memcpy (dest + TOP_RANGE, source + top, 16);
	else
		for (j=0 ; j<16 ; j++)
			dest[TOP_RANGE+j] = source[top+15-j];

	if (bottom < 128)
		memcpy (dest + BOTTOM_RANGE, source + bottom, 16);
	else
		for (j=0 ; j<16 ; j++)
			dest[BOTTOM_RANGE+j] = source[bottom+15-j];
}


void M.DrawTransPicTranslate (int x, int y, qpic_t *pic)
{
	Draw.TransPicTranslate (x + ((vid.width - 320)>>1), y, pic, translationTable);
}


void M.DrawTextBox (int x, int y, int width, int lines)
{
	qpic_t	*p;
	int		cx, cy;
	int		n;

	// draw left side
	cx = x;
	cy = y;
	p = Draw.CachePic ("gfx/box_tl.lmp");
	M.DrawTransPic (cx, cy, p);
	p = Draw.CachePic ("gfx/box_ml.lmp");
	for (n = 0; n < lines; n++)
	{
		cy += 8;
		M.DrawTransPic (cx, cy, p);
	}
	p = Draw.CachePic ("gfx/box_bl.lmp");
	M.DrawTransPic (cx, cy+8, p);

	// draw middle
	cx += 8;
	while (width > 0)
	{
		cy = y;
		p = Draw.CachePic ("gfx/box_tm.lmp");
		M.DrawTransPic (cx, cy, p);
		p = Draw.CachePic ("gfx/box_mm.lmp");
		for (n = 0; n < lines; n++)
		{
			cy += 8;
			if (n == 1)
				p = Draw.CachePic ("gfx/box_mm2.lmp");
			M.DrawTransPic (cx, cy, p);
		}
		p = Draw.CachePic ("gfx/box_bm.lmp");
		M.DrawTransPic (cx, cy+8, p);
		width -= 2;
		cx += 16;
	}

	// draw right side
	cy = y;
	p = Draw.CachePic ("gfx/box_tr.lmp");
	M.DrawTransPic (cx, cy, p);
	p = Draw.CachePic ("gfx/box_mr.lmp");
	for (n = 0; n < lines; n++)
	{
		cy += 8;
		M.DrawTransPic (cx, cy, p);
	}
	p = Draw.CachePic ("gfx/box_br.lmp");
	M.DrawTransPic (cx, cy+8, p);
}

//=============================================================================

int m_save_demonum;

/*
================
M.ToggleMenu_f
================
*/
void M.ToggleMenu_f ()
{
	m_entersound = true;

	if (key_dest == key_menu)
	{
		if (m_state != m_main)
		{
			M.Menu_Main_f ();
			return;
		}
		key_dest = key_game;
		m_state = m_none;
		return;
	}
	if (key_dest == key_console)
	{
		Con.ToggleConsole_f ();
	}
	else
	{
		M.Menu_Main_f ();
	}
}


//=============================================================================
/* MAIN MENU */

int	m_main_cursor;
#define	MAIN_ITEMS	5


void M.Menu_Main_f ()
{
	if (key_dest != key_menu)
	{
		m_save_demonum = cls.demonum;
		cls.demonum = -1;
	}
	key_dest = key_menu;
	m_state = m_main;
	m_entersound = true;
}


void M.Main_Draw ()
{
	int		f;
	qpic_t	*p;

	M.DrawTransPic (16, 4, Draw.CachePic ("gfx/qplaque.lmp") );
	p = Draw.CachePic ("gfx/ttl_main.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);
	M.DrawTransPic (72, 32, Draw.CachePic ("gfx/mainmenu.lmp") );

	f = (int)(host_time * 10)%6;

	M.DrawTransPic (54, 32 + m_main_cursor * 20,Draw.CachePic( va("gfx/menudot%i.lmp", f+1 ) ) );
}


void M.Main_Key (int key)
{
	switch (key)
	{
	case K_ESCAPE:
		key_dest = key_game;
		m_state = m_none;
		cls.demonum = m_save_demonum;
		if (cls.demonum != -1 && !cls.demoplayback && cls.state != ca_connected)
			CL.NextDemo ();
		break;

	case K_DOWNARROW:
		S.LocalSound ("misc/menu1.wav");
		if (++m_main_cursor >= MAIN_ITEMS)
			m_main_cursor = 0;
		break;

	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		if (--m_main_cursor < 0)
			m_main_cursor = MAIN_ITEMS - 1;
		break;

	case K_ENTER:
		m_entersound = true;

		switch (m_main_cursor)
		{
		case 0:
			M.Menu_SinglePlayer_f ();
			break;

		case 1:
			M.Menu_MultiPlayer_f ();
			break;

		case 2:
			M.Menu_Options_f ();
			break;

		case 3:
			M.Menu_Help_f ();
			break;

		case 4:
			M.Menu_Quit_f ();
			break;
		}
	}
}

//=============================================================================
/* SINGLE PLAYER MENU */

int	m_singleplayer_cursor;
#define	SINGLEPLAYER_ITEMS	3


void M.Menu_SinglePlayer_f ()
{
	key_dest = key_menu;
	m_state = m_singleplayer;
	m_entersound = true;
}


void M.SinglePlayer_Draw ()
{
	int		f;
	qpic_t	*p;

	M.DrawTransPic (16, 4, Draw.CachePic ("gfx/qplaque.lmp") );
	p = Draw.CachePic ("gfx/ttl_sgl.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);
	M.DrawTransPic (72, 32, Draw.CachePic ("gfx/sp_menu.lmp") );

	f = (int)(host_time * 10)%6;

	M.DrawTransPic (54, 32 + m_singleplayer_cursor * 20,Draw.CachePic( va("gfx/menudot%i.lmp", f+1 ) ) );
}


void M.SinglePlayer_Key (int key)
{
	switch (key)
	{
	case K_ESCAPE:
		M.Menu_Main_f ();
		break;

	case K_DOWNARROW:
		S.LocalSound ("misc/menu1.wav");
		if (++m_singleplayer_cursor >= SINGLEPLAYER_ITEMS)
			m_singleplayer_cursor = 0;
		break;

	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		if (--m_singleplayer_cursor < 0)
			m_singleplayer_cursor = SINGLEPLAYER_ITEMS - 1;
		break;

	case K_ENTER:
		m_entersound = true;

		switch (m_singleplayer_cursor)
		{
		case 0:
			if (sv.active)
				if (!SCR.ModalMessage("Are you sure you want to\nstart a new game?\n"))
					break;
			key_dest = key_game;
			if (sv.active)
				Cbuf.AddText ("disconnect\n");
			Cbuf.AddText ("maxplayers 1\n");
			Cbuf.AddText ("map start\n");
			break;

		case 1:
			M.Menu_Load_f ();
			break;

		case 2:
			M.Menu_Save_f ();
			break;
		}
	}
}

//=============================================================================
/* LOAD/SAVE MENU */

int		load_cursor;		// 0 < load_cursor < MAX_SAVEGAMES

#define	MAX_SAVEGAMES		12
char	m_filenames[MAX_SAVEGAMES][SAVEGAME_COMMENT_LENGTH+1];
int		loadable[MAX_SAVEGAMES];

void M.ScanSaves ()
{
	int		i, j;
	char	name[MAX_OSPATH];
	FILE	*f;
	int		version;

	for (i=0 ; i<MAX_SAVEGAMES ; i++)
	{
		strcpy (m_filenames[i], "--- UNUSED SLOT ---");
		loadable[i] = false;
		sprintf (name, "%s/s%i.sav", com_gamedir, i);
		f = fopen (name, "r");
		if (!f)
			continue;
		fscanf (f, "%i\n", &version);
		fscanf (f, "%79s\n", name);
		strncpy (m_filenames[i], name, sizeof(m_filenames[i])-1);

	// change _ back to space
		for (j=0 ; j<SAVEGAME_COMMENT_LENGTH ; j++)
			if (m_filenames[i][j] == '_')
				m_filenames[i][j] = ' ';
		loadable[i] = true;
		fclose (f);
	}
}

void M.Menu_Load_f ()
{
	m_entersound = true;
	m_state = m_load;
	key_dest = key_menu;
	M.ScanSaves ();
}


void M.Menu_Save_f ()
{
	if (!sv.active)
		return;
	if (cl.intermission)
		return;
	if (svs.maxclients != 1)
		return;
	m_entersound = true;
	m_state = m_save;
	key_dest = key_menu;
	M.ScanSaves ();
}


void M.Load_Draw ()
{
	int		i;
	qpic_t	*p;

	p = Draw.CachePic ("gfx/p_load.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);

	for (i=0 ; i< MAX_SAVEGAMES; i++)
		M.Print (16, 32 + 8*i, m_filenames[i]);

// line cursor
	M.DrawCharacter (8, 32 + load_cursor*8, 12+((int)(realtime*4)&1));
}


void M.Save_Draw ()
{
	int		i;
	qpic_t	*p;

	p = Draw.CachePic ("gfx/p_save.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);

	for (i=0 ; i<MAX_SAVEGAMES ; i++)
		M.Print (16, 32 + 8*i, m_filenames[i]);

// line cursor
	M.DrawCharacter (8, 32 + load_cursor*8, 12+((int)(realtime*4)&1));
}


void M.Load_Key (int k)
{
	switch (k)
	{
	case K_ESCAPE:
		M.Menu_SinglePlayer_f ();
		break;

	case K_ENTER:
		S.LocalSound ("misc/menu2.wav");
		if (!loadable[load_cursor])
			return;
		m_state = m_none;
		key_dest = key_game;

	// Host.Loadgame_f can't bring up the loading plaque because too much
	// stack space has been used, so do it now
		SCR.BeginLoadingPlaque ();

	// issue the load command
		Cbuf.AddText (va ("load s%i\n", load_cursor) );
		return;

	case K_UPARROW:
	case K_LEFTARROW:
		S.LocalSound ("misc/menu1.wav");
		load_cursor--;
		if (load_cursor < 0)
			load_cursor = MAX_SAVEGAMES-1;
		break;

	case K_DOWNARROW:
	case K_RIGHTARROW:
		S.LocalSound ("misc/menu1.wav");
		load_cursor++;
		if (load_cursor >= MAX_SAVEGAMES)
			load_cursor = 0;
		break;
	}
}


void M.Save_Key (int k)
{
	switch (k)
	{
	case K_ESCAPE:
		M.Menu_SinglePlayer_f ();
		break;

	case K_ENTER:
		m_state = m_none;
		key_dest = key_game;
		Cbuf.AddText (va("save s%i\n", load_cursor));
		return;

	case K_UPARROW:
	case K_LEFTARROW:
		S.LocalSound ("misc/menu1.wav");
		load_cursor--;
		if (load_cursor < 0)
			load_cursor = MAX_SAVEGAMES-1;
		break;

	case K_DOWNARROW:
	case K_RIGHTARROW:
		S.LocalSound ("misc/menu1.wav");
		load_cursor++;
		if (load_cursor >= MAX_SAVEGAMES)
			load_cursor = 0;
		break;
	}
}

//=============================================================================
/* MULTIPLAYER MENU */

int	m_multiplayer_cursor;
#define	MULTIPLAYER_ITEMS	3


void M.Menu_MultiPlayer_f ()
{
	key_dest = key_menu;
	m_state = m_multiplayer;
	m_entersound = true;
}


void M.MultiPlayer_Draw ()
{
	int		f;
	qpic_t	*p;

	M.DrawTransPic (16, 4, Draw.CachePic ("gfx/qplaque.lmp") );
	p = Draw.CachePic ("gfx/p_multi.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);
	M.DrawTransPic (72, 32, Draw.CachePic ("gfx/mp_menu.lmp") );

	f = (int)(host_time * 10)%6;

	M.DrawTransPic (54, 32 + m_multiplayer_cursor * 20,Draw.CachePic( va("gfx/menudot%i.lmp", f+1 ) ) );

	if (serialAvailable || ipxAvailable || tcpipAvailable)
		return;
	M.PrintWhite ((320/2) - ((27*8)/2), 148, "No Communications Available");
}


void M.MultiPlayer_Key (int key)
{
	switch (key)
	{
	case K_ESCAPE:
		M.Menu_Main_f ();
		break;

	case K_DOWNARROW:
		S.LocalSound ("misc/menu1.wav");
		if (++m_multiplayer_cursor >= MULTIPLAYER_ITEMS)
			m_multiplayer_cursor = 0;
		break;

	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		if (--m_multiplayer_cursor < 0)
			m_multiplayer_cursor = MULTIPLAYER_ITEMS - 1;
		break;

	case K_ENTER:
		m_entersound = true;
		switch (m_multiplayer_cursor)
		{
		case 0:
			if (serialAvailable || ipxAvailable || tcpipAvailable)
				M.Menu_Net_f ();
			break;

		case 1:
			if (serialAvailable || ipxAvailable || tcpipAvailable)
				M.Menu_Net_f ();
			break;

		case 2:
			M.Menu_Setup_f ();
			break;
		}
	}
}

//=============================================================================
/* SETUP MENU */

int		setup_cursor = 4;
int		setup_cursor_table[] = {40, 56, 80, 104, 140};

char	setup_hostname[16];
char	setup_myname[16];
int		setup_oldtop;
int		setup_oldbottom;
int		setup_top;
int		setup_bottom;

#define	NUM_SETUP_CMDS	5

void M.Menu_Setup_f ()
{
	key_dest = key_menu;
	m_state = m_setup;
	m_entersound = true;
	Q.strcpy(setup_myname, cl_name.string);
	Q.strcpy(setup_hostname, hostname.string);
	setup_top = setup_oldtop = ((int)cl_color.value) >> 4;
	setup_bottom = setup_oldbottom = ((int)cl_color.value) & 15;
}


void M.Setup_Draw ()
{
	qpic_t	*p;

	M.DrawTransPic (16, 4, Draw.CachePic ("gfx/qplaque.lmp") );
	p = Draw.CachePic ("gfx/p_multi.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);

	M.Print (64, 40, "Hostname");
	M.DrawTextBox (160, 32, 16, 1);
	M.Print (168, 40, setup_hostname);

	M.Print (64, 56, "Your name");
	M.DrawTextBox (160, 48, 16, 1);
	M.Print (168, 56, setup_myname);

	M.Print (64, 80, "Shirt color");
	M.Print (64, 104, "Pants color");

	M.DrawTextBox (64, 140-8, 14, 1);
	M.Print (72, 140, "Accept Changes");

	p = Draw.CachePic ("gfx/bigbox.lmp");
	M.DrawTransPic (160, 64, p);
	p = Draw.CachePic ("gfx/menuplyr.lmp");
	M.BuildTranslationTable(setup_top*16, setup_bottom*16);
	M.DrawTransPicTranslate (172, 72, p);

	M.DrawCharacter (56, setup_cursor_table [setup_cursor], 12+((int)(realtime*4)&1));

	if (setup_cursor == 0)
		M.DrawCharacter (168 + 8*strlen(setup_hostname), setup_cursor_table [setup_cursor], 10+((int)(realtime*4)&1));

	if (setup_cursor == 1)
		M.DrawCharacter (168 + 8*strlen(setup_myname), setup_cursor_table [setup_cursor], 10+((int)(realtime*4)&1));
}


void M.Setup_Key (int k)
{
	int			l;

	switch (k)
	{
	case K_ESCAPE:
		M.Menu_MultiPlayer_f ();
		break;

	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		setup_cursor--;
		if (setup_cursor < 0)
			setup_cursor = NUM_SETUP_CMDS-1;
		break;

	case K_DOWNARROW:
		S.LocalSound ("misc/menu1.wav");
		setup_cursor++;
		if (setup_cursor >= NUM_SETUP_CMDS)
			setup_cursor = 0;
		break;

	case K_LEFTARROW:
		if (setup_cursor < 2)
			return;
		S.LocalSound ("misc/menu3.wav");
		if (setup_cursor == 2)
			setup_top = setup_top - 1;
		if (setup_cursor == 3)
			setup_bottom = setup_bottom - 1;
		break;
	case K_RIGHTARROW:
		if (setup_cursor < 2)
			return;
forward:
		S.LocalSound ("misc/menu3.wav");
		if (setup_cursor == 2)
			setup_top = setup_top + 1;
		if (setup_cursor == 3)
			setup_bottom = setup_bottom + 1;
		break;

	case K_ENTER:
		if (setup_cursor == 0 || setup_cursor == 1)
			return;

		if (setup_cursor == 2 || setup_cursor == 3)
			goto forward;

		// setup_cursor == 4 (OK)
		if (Q.strcmp(cl_name.string, setup_myname) != 0)
			Cbuf.AddText ( va ("name \"%s\"\n", setup_myname) );
		if (Q.strcmp(hostname.string, setup_hostname) != 0)
			Cvar.Set("hostname", setup_hostname);
		if (setup_top != setup_oldtop || setup_bottom != setup_oldbottom)
			Cbuf.AddText( va ("color %i %i\n", setup_top, setup_bottom) );
		m_entersound = true;
		M.Menu_MultiPlayer_f ();
		break;

	case K_BACKSPACE:
		if (setup_cursor == 0)
		{
			if (strlen(setup_hostname))
				setup_hostname[strlen(setup_hostname)-1] = 0;
		}

		if (setup_cursor == 1)
		{
			if (strlen(setup_myname))
				setup_myname[strlen(setup_myname)-1] = 0;
		}
		break;

	default:
		if (k < 32 || k > 127)
			break;
		if (setup_cursor == 0)
		{
			l = strlen(setup_hostname);
			if (l < 15)
			{
				setup_hostname[l+1] = 0;
				setup_hostname[l] = k;
			}
		}
		if (setup_cursor == 1)
		{
			l = strlen(setup_myname);
			if (l < 15)
			{
				setup_myname[l+1] = 0;
				setup_myname[l] = k;
			}
		}
	}

	if (setup_top > 13)
		setup_top = 0;
	if (setup_top < 0)
		setup_top = 13;
	if (setup_bottom > 13)
		setup_bottom = 0;
	if (setup_bottom < 0)
		setup_bottom = 13;
}

//=============================================================================
/* NET MENU */

int	m_net_cursor;
int m_net_items;
int m_net_saveHeight;

char *net_helpMessage [] =
{
/* .........1.........2.... */
  "                        ",
  " Two computers connected",
  "   through two modems.  ",
  "                        ",

  "                        ",
  " Two computers connected",
  " by a null-modem cable. ",
  "                        ",

  " Novell network LANs    ",
  " or Windows 95 DOS-box. ",
  "                        ",
  "(LAN=Local Area Network)",

  " Commonly used to play  ",
  " over the Internet, but ",
  " also used on a Local   ",
  " Area Network.          "
};

void M.Menu_Net_f ()
{
	key_dest = key_menu;
	m_state = m_net;
	m_entersound = true;
	m_net_items = 4;

	if (m_net_cursor >= m_net_items)
		m_net_cursor = 0;
	m_net_cursor--;
	M.Net_Key (K_DOWNARROW);
}


void M.Net_Draw ()
{
	int		f;
	qpic_t	*p;

	M.DrawTransPic (16, 4, Draw.CachePic ("gfx/qplaque.lmp") );
	p = Draw.CachePic ("gfx/p_multi.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);

	f = 32;

	if (serialAvailable)
	{
		p = Draw.CachePic ("gfx/netmen1.lmp");
	}
	else
	{
#ifdef _WIN32
		p = null;
#else
		p = Draw.CachePic ("gfx/dim_modm.lmp");
#endif
	}

	if (p)
		M.DrawTransPic (72, f, p);

	f += 19;

	if (serialAvailable)
	{
		p = Draw.CachePic ("gfx/netmen2.lmp");
	}
	else
	{
#ifdef _WIN32
		p = null;
#else
		p = Draw.CachePic ("gfx/dim_drct.lmp");
#endif
	}

	if (p)
		M.DrawTransPic (72, f, p);

	f += 19;
	if (ipxAvailable)
		p = Draw.CachePic ("gfx/netmen3.lmp");
	else
		p = Draw.CachePic ("gfx/dim_ipx.lmp");
	M.DrawTransPic (72, f, p);

	f += 19;
	if (tcpipAvailable)
		p = Draw.CachePic ("gfx/netmen4.lmp");
	else
		p = Draw.CachePic ("gfx/dim_tcp.lmp");
	M.DrawTransPic (72, f, p);

	if (m_net_items == 5)	// JDC, could just be removed
	{
		f += 19;
		p = Draw.CachePic ("gfx/netmen5.lmp");
		M.DrawTransPic (72, f, p);
	}

	f = (320-26*8)/2;
	M.DrawTextBox (f, 134, 24, 4);
	f += 8;
	M.Print (f, 142, net_helpMessage[m_net_cursor*4+0]);
	M.Print (f, 150, net_helpMessage[m_net_cursor*4+1]);
	M.Print (f, 158, net_helpMessage[m_net_cursor*4+2]);
	M.Print (f, 166, net_helpMessage[m_net_cursor*4+3]);

	f = (int)(host_time * 10)%6;
	M.DrawTransPic (54, 32 + m_net_cursor * 20,Draw.CachePic( va("gfx/menudot%i.lmp", f+1 ) ) );
}


void M.Net_Key (int k)
{
again:
	switch (k)
	{
	case K_ESCAPE:
		M.Menu_MultiPlayer_f ();
		break;

	case K_DOWNARROW:
		S.LocalSound ("misc/menu1.wav");
		if (++m_net_cursor >= m_net_items)
			m_net_cursor = 0;
		break;

	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		if (--m_net_cursor < 0)
			m_net_cursor = m_net_items - 1;
		break;

	case K_ENTER:
		m_entersound = true;

		switch (m_net_cursor)
		{
		case 0:
			M.Menu_SerialConfig_f ();
			break;

		case 1:
			M.Menu_SerialConfig_f ();
			break;

		case 2:
			M.Menu_LanConfig_f ();
			break;

		case 3:
			M.Menu_LanConfig_f ();
			break;

		case 4:
// multiprotocol
			break;
		}
	}

	if (m_net_cursor == 0 && !serialAvailable)
		goto again;
	if (m_net_cursor == 1 && !serialAvailable)
		goto again;
	if (m_net_cursor == 2 && !ipxAvailable)
		goto again;
	if (m_net_cursor == 3 && !tcpipAvailable)
		goto again;
}

//=============================================================================
/* OPTIONS MENU */

#ifdef _WIN32
#define	OPTIONS_ITEMS	14
#else
#define	OPTIONS_ITEMS	13
#endif

#define	SLIDER_RANGE	10

int		options_cursor;

void M.Menu_Options_f ()
{
	key_dest = key_menu;
	m_state = m_options;
	m_entersound = true;

#ifdef _WIN32
	if ((options_cursor == 13) && (modestate != MS_WINDOWED))
	{
		options_cursor = 0;
	}
#endif
}


void M.AdjustSliders (int dir)
{
	S.LocalSound ("misc/menu3.wav");

	switch (options_cursor)
	{
	case 3:	// screen size
		scr_viewsize.value += dir * 10;
		if (scr_viewsize.value < 30)
			scr_viewsize.value = 30;
		if (scr_viewsize.value > 120)
			scr_viewsize.value = 120;
		Cvar.SetValue ("viewsize", scr_viewsize.value);
		break;
	case 4:	// gamma
		v_gamma.value -= dir * 0.05;
		if (v_gamma.value < 0.5)
			v_gamma.value = 0.5;
		if (v_gamma.value > 1)
			v_gamma.value = 1;
		Cvar.SetValue ("gamma", v_gamma.value);
		break;
	case 5:	// mouse speed
		sensitivity.value += dir * 0.5;
		if (sensitivity.value < 1)
			sensitivity.value = 1;
		if (sensitivity.value > 11)
			sensitivity.value = 11;
		Cvar.SetValue ("sensitivity", sensitivity.value);
		break;
	case 6:	// music volume
#ifdef _WIN32
		bgmvolume.value += dir * 1.0;
#else
		bgmvolume.value += dir * 0.1;
#endif
		if (bgmvolume.value < 0)
			bgmvolume.value = 0;
		if (bgmvolume.value > 1)
			bgmvolume.value = 1;
		Cvar.SetValue ("bgmvolume", bgmvolume.value);
		break;
	case 7:	// sfx volume
		volume.value += dir * 0.1;
		if (volume.value < 0)
			volume.value = 0;
		if (volume.value > 1)
			volume.value = 1;
		Cvar.SetValue ("volume", volume.value);
		break;

	case 8:	// allways run
		if (cl_forwardspeed.value > 200)
		{
			Cvar.SetValue ("cl_forwardspeed", 200);
			Cvar.SetValue ("cl_backspeed", 200);
		}
		else
		{
			Cvar.SetValue ("cl_forwardspeed", 400);
			Cvar.SetValue ("cl_backspeed", 400);
		}
		break;

	case 9:	// invert mouse
		Cvar.SetValue ("m_pitch", -m_pitch.value);
		break;

	case 10:	// lookspring
		Cvar.SetValue ("lookspring", !lookspring.value);
		break;

	case 11:	// lookstrafe
		Cvar.SetValue ("lookstrafe", !lookstrafe.value);
		break;

#ifdef _WIN32
	case 13:	// _windowed_mouse
		Cvar.SetValue ("_windowed_mouse", !_windowed_mouse.value);
		break;
#endif
	}
}


void M.DrawSlider (int x, int y, float range)
{
	int	i;

	if (range < 0)
		range = 0;
	if (range > 1)
		range = 1;
	M.DrawCharacter (x-8, y, 128);
	for (i=0 ; i<SLIDER_RANGE ; i++)
		M.DrawCharacter (x + i*8, y, 129);
	M.DrawCharacter (x+i*8, y, 130);
	M.DrawCharacter (x + (SLIDER_RANGE-1)*8 * range, y, 131);
}

void M.DrawCheckbox (int x, int y, int on)
{
#if 0
	if (on)
		M.DrawCharacter (x, y, 131);
	else
		M.DrawCharacter (x, y, 129);
#endif
	if (on)
		M.Print (x, y, "on");
	else
		M.Print (x, y, "off");
}

void M.Options_Draw ()
{
	float		r;
	qpic_t	*p;

	M.DrawTransPic (16, 4, Draw.CachePic ("gfx/qplaque.lmp") );
	p = Draw.CachePic ("gfx/p_option.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);

	M.Print (16, 32, "    Customize controls");
	M.Print (16, 40, "         Go to console");
	M.Print (16, 48, "     Reset to defaults");

	M.Print (16, 56, "           Screen size");
	r = (scr_viewsize.value - 30) / (120 - 30);
	M.DrawSlider (220, 56, r);

	M.Print (16, 64, "            Brightness");
	r = (1.0 - v_gamma.value) / 0.5;
	M.DrawSlider (220, 64, r);

	M.Print (16, 72, "           Mouse Speed");
	r = (sensitivity.value - 1)/10;
	M.DrawSlider (220, 72, r);

	M.Print (16, 80, "       CD Music Volume");
	r = bgmvolume.value;
	M.DrawSlider (220, 80, r);

	M.Print (16, 88, "          Sound Volume");
	r = volume.value;
	M.DrawSlider (220, 88, r);

	M.Print (16, 96,  "            Always Run");
	M.DrawCheckbox (220, 96, cl_forwardspeed.value > 200);

	M.Print (16, 104, "          Invert Mouse");
	M.DrawCheckbox (220, 104, m_pitch.value < 0);

	M.Print (16, 112, "            Lookspring");
	M.DrawCheckbox (220, 112, lookspring.value);

	M.Print (16, 120, "            Lookstrafe");
	M.DrawCheckbox (220, 120, lookstrafe.value);

	if (vid_menudrawfn)
		M.Print (16, 128, "         Video Options");

#ifdef _WIN32
	if (modestate == MS_WINDOWED)
	{
		M.Print (16, 136, "             Use Mouse");
		M.DrawCheckbox (220, 136, _windowed_mouse.value);
	}
#endif

// cursor
	M.DrawCharacter (200, 32 + options_cursor*8, 12+((int)(realtime*4)&1));
}


void M.Options_Key (int k)
{
	switch (k)
	{
	case K_ESCAPE:
		M.Menu_Main_f ();
		break;

	case K_ENTER:
		m_entersound = true;
		switch (options_cursor)
		{
		case 0:
			M.Menu_Keys_f ();
			break;
		case 1:
			m_state = m_none;
			Con.ToggleConsole_f ();
			break;
		case 2:
			Cbuf.AddText ("exec default.cfg\n");
			break;
		case 12:
			M.Menu_Video_f ();
			break;
		default:
			M.AdjustSliders (1);
			break;
		}
		return;

	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		options_cursor--;
		if (options_cursor < 0)
			options_cursor = OPTIONS_ITEMS-1;
		break;

	case K_DOWNARROW:
		S.LocalSound ("misc/menu1.wav");
		options_cursor++;
		if (options_cursor >= OPTIONS_ITEMS)
			options_cursor = 0;
		break;

	case K_LEFTARROW:
		M.AdjustSliders (-1);
		break;

	case K_RIGHTARROW:
		M.AdjustSliders (1);
		break;
	}

	if (options_cursor == 12 && vid_menudrawfn == null)
	{
		if (k == K_UPARROW)
			options_cursor = 11;
		else
			options_cursor = 0;
	}

#ifdef _WIN32
	if ((options_cursor == 13) && (modestate != MS_WINDOWED))
	{
		if (k == K_UPARROW)
			options_cursor = 12;
		else
			options_cursor = 0;
	}
#endif
}

//=============================================================================
/* KEYS MENU */

char *bindnames[][2] =
{
{"+attack", 		"attack"},
{"impulse 10", 		"change weapon"},
{"+jump", 			"jump / swim up"},
{"+forward", 		"walk forward"},
{"+back", 			"backpedal"},
{"+left", 			"turn left"},
{"+right", 			"turn right"},
{"+speed", 			"run"},
{"+moveleft", 		"step left"},
{"+moveright", 		"step right"},
{"+strafe", 		"sidestep"},
{"+lookup", 		"look up"},
{"+lookdown", 		"look down"},
{"centerview", 		"center view"},
{"+mlook", 			"mouse look"},
{"+klook", 			"keyboard look"},
{"+moveup",			"swim up"},
{"+movedown",		"swim down"}
};

#define	NUMCOMMANDS	(sizeof(bindnames)/sizeof(bindnames[0]))

int		keys_cursor;
int		bind_grab;

void M.Menu_Keys_f ()
{
	key_dest = key_menu;
	m_state = m_keys;
	m_entersound = true;
}


void M.FindKeysForCommand (char *command, int *twokeys)
{
	int		count;
	int		j;
	int		l;
	char	*b;

	twokeys[0] = twokeys[1] = -1;
	l = strlen(command);
	count = 0;

	for (j=0 ; j<256 ; j++)
	{
		b = keybindings[j];
		if (!b)
			continue;
		if (!strncmp (b, command, l) )
		{
			twokeys[count] = j;
			count++;
			if (count == 2)
				break;
		}
	}
}

void M.UnbindCommand (char *command)
{
	int		j;
	int		l;
	char	*b;

	l = strlen(command);

	for (j=0 ; j<256 ; j++)
	{
		b = keybindings[j];
		if (!b)
			continue;
		if (!strncmp (b, command, l) )
			Key.SetBinding (j, "");
	}
}


void M.Keys_Draw ()
{
	int		i, l;
	int		keys[2];
	char	*name;
	int		x, y;
	qpic_t	*p;

	p = Draw.CachePic ("gfx/ttl_cstm.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);

	if (bind_grab)
		M.Print (12, 32, "Press a key or button for this action");
	else
		M.Print (18, 32, "Enter to change, backspace to clear");

// search for known bindings
	for (i=0 ; i<NUMCOMMANDS ; i++)
	{
		y = 48 + 8*i;

		M.Print (16, y, bindnames[i][1]);

		l = strlen (bindnames[i][0]);

		M.FindKeysForCommand (bindnames[i][0], keys);

		if (keys[0] == -1)
		{
			M.Print (140, y, "???");
		}
		else
		{
			name = Key.KeynumToString (keys[0]);
			M.Print (140, y, name);
			x = strlen(name) * 8;
			if (keys[1] != -1)
			{
				M.Print (140 + x + 8, y, "or");
				M.Print (140 + x + 32, y, Key.KeynumToString (keys[1]));
			}
		}
	}

	if (bind_grab)
		M.DrawCharacter (130, 48 + keys_cursor*8, '=');
	else
		M.DrawCharacter (130, 48 + keys_cursor*8, 12+((int)(realtime*4)&1));
}


void M.Keys_Key (int k)
{
	char	cmd[80];
	int		keys[2];

	if (bind_grab)
	{	// defining a key
		S.LocalSound ("misc/menu1.wav");
		if (k == K_ESCAPE)
		{
			bind_grab = false;
		}
		else if (k != '`')
		{
			sprintf (cmd, "bind \"%s\" \"%s\"\n", Key.KeynumToString (k), bindnames[keys_cursor][0]);
			Cbuf.InsertText (cmd);
		}

		bind_grab = false;
		return;
	}

	switch (k)
	{
	case K_ESCAPE:
		M.Menu_Options_f ();
		break;

	case K_LEFTARROW:
	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		keys_cursor--;
		if (keys_cursor < 0)
			keys_cursor = NUMCOMMANDS-1;
		break;

	case K_DOWNARROW:
	case K_RIGHTARROW:
		S.LocalSound ("misc/menu1.wav");
		keys_cursor++;
		if (keys_cursor >= NUMCOMMANDS)
			keys_cursor = 0;
		break;

	case K_ENTER:		// go into bind mode
		M.FindKeysForCommand (bindnames[keys_cursor][0], keys);
		S.LocalSound ("misc/menu2.wav");
		if (keys[1] != -1)
			M.UnbindCommand (bindnames[keys_cursor][0]);
		bind_grab = true;
		break;

	case K_BACKSPACE:		// delete bindings
	case K_DEL:				// delete bindings
		S.LocalSound ("misc/menu2.wav");
		M.UnbindCommand (bindnames[keys_cursor][0]);
		break;
	}
}

//=============================================================================
/* VIDEO MENU */

void M.Menu_Video_f ()
{
	key_dest = key_menu;
	m_state = m_video;
	m_entersound = true;
}


void M.Video_Draw ()
{
	(*vid_menudrawfn) ();
}


void M.Video_Key (int key)
{
	(*vid_menukeyfn) (key);
}

//=============================================================================
/* HELP MENU */

int		help_page;
#define	NUM_HELP_PAGES	6


void M.Menu_Help_f ()
{
	key_dest = key_menu;
	m_state = m_help;
	m_entersound = true;
	help_page = 0;
}



void M.Help_Draw ()
{
	M.DrawPic (0, 0, Draw.CachePic ( va("gfx/help%i.lmp", help_page)) );
}


void M.Help_Key (int key)
{
	switch (key)
	{
	case K_ESCAPE:
		M.Menu_Main_f ();
		break;

	case K_UPARROW:
	case K_RIGHTARROW:
		m_entersound = true;
		if (++help_page >= NUM_HELP_PAGES)
			help_page = 0;
		break;

	case K_DOWNARROW:
	case K_LEFTARROW:
		m_entersound = true;
		if (--help_page < 0)
			help_page = NUM_HELP_PAGES-1;
		break;
	}

}

//=============================================================================
/* QUIT MENU */

int		msgNumber;
int		m_quit_prevstate;
boolean	wasInMenus;

#ifndef	_WIN32
char *quitMessage [] = 
{
/* .........1.........2.... */
  "  Are you gonna quit    ",
  "  this game just like   ",
  "   everything else?     ",
  "                        ",
 
  " Milord, methinks that  ",
  "   thou art a lowly     ",
  " quitter. Is this true? ",
  "                        ",

  " Do I need to bust your ",
  "  face open for trying  ",
  "        to quit?        ",
  "                        ",

  " Man, I oughta smack you",
  "   for trying to quit!  ",
  "     Press Y to get     ",
  "      smacked out.      ",
 
  " Press Y to quit like a ",
  "   big loser in life.   ",
  "  Press N to stay proud ",
  "    and successful!     ",
 
  "   If you press Y to    ",
  "  quit, I will summon   ",
  "  Satan all over your   ",
  "      hard drive!       ",
 
  "  Um, Asmodeus dislikes ",
  " his children trying to ",
  " quit. Press Y to return",
  "   to your Tinkertoys.  ",
 
  "  If you quit now, I'll ",
  "  throw a blanket-party ",
  "   for you next time!   ",
  "                        "
};
#endif

void M.Menu_Quit_f ()
{
	if (m_state == m_quit)
		return;
	wasInMenus = (key_dest == key_menu);
	key_dest = key_menu;
	m_quit_prevstate = m_state;
	m_state = m_quit;
	m_entersound = true;
	msgNumber = rand()&7;
}


void M.Quit_Key (int key)
{
	switch (key)
	{
	case K_ESCAPE:
	case 'n':
	case 'N':
		if (wasInMenus)
		{
			m_state = m_quit_prevstate;
			m_entersound = true;
		}
		else
		{
			key_dest = key_game;
			m_state = m_none;
		}
		break;

	case 'Y':
	case 'y':
		key_dest = key_console;
		Host.Quit_f ();
		break;

	default:
		break;
	}

}


void M.Quit_Draw ()
{
	if (wasInMenus)
	{
		m_state = m_quit_prevstate;
		m_recursiveDraw = true;
		M.Draw ();
		m_state = m_quit;
	}

#ifdef _WIN32
	M.DrawTextBox (0, 0, 38, 23);
	M.PrintWhite (16, 12,  "  Quake version 1.09 by id Software\n\n");
	M.PrintWhite (16, 28,  "Programming        Art \n");
	M.Print (16, 36,  " John Carmack       Adrian Carmack\n");
	M.Print (16, 44,  " Michael Abrash     Kevin Cloud\n");
	M.Print (16, 52,  " John Cash          Paul Steed\n");
	M.Print (16, 60,  " Dave 'Zoid' Kirsch\n");
	M.PrintWhite (16, 68,  "Design             Biz\n");
	M.Print (16, 76,  " John Romero        Jay Wilbur\n");
	M.Print (16, 84,  " Sandy Petersen     Mike Wilson\n");
	M.Print (16, 92,  " American McGee     Donna Jackson\n");
	M.Print (16, 100,  " Tim Willits        Todd Hollenshead\n");
	M.PrintWhite (16, 108, "Support            Projects\n");
	M.Print (16, 116, " Barrett Alexander  Shawn Green\n");
	M.PrintWhite (16, 124, "Sound Effects\n");
	M.Print (16, 132, " Trent Reznor and Nine Inch Nails\n\n");
	M.PrintWhite (16, 140, "Quake is a trademark of Id Software,\n");
	M.PrintWhite (16, 148, "inc., (c)1996 Id Software, inc. All\n");
	M.PrintWhite (16, 156, "rights reserved. NIN logo is a\n");
	M.PrintWhite (16, 164, "registered trademark licensed to\n");
	M.PrintWhite (16, 172, "Nothing Interactive, Inc. All rights\n");
	M.PrintWhite (16, 180, "reserved. Press y to exit\n");
#else
	M.DrawTextBox (56, 76, 24, 4);
	M.Print (64, 84,  quitMessage[msgNumber*4+0]);
	M.Print (64, 92,  quitMessage[msgNumber*4+1]);
	M.Print (64, 100, quitMessage[msgNumber*4+2]);
	M.Print (64, 108, quitMessage[msgNumber*4+3]);
#endif
}

//=============================================================================

/* SERIAL CONFIG MENU */

int		serialConfig_cursor;
int		serialConfig_cursor_table[] = {48, 64, 80, 96, 112, 132};
#define	NUM_SERIALCONFIG_CMDS	6

static int ISA_uarts[]	= {0x3f8,0x2f8,0x3e8,0x2e8};
static int ISA_IRQs[]	= {4,3,4,3};
int serialConfig_baudrate[] = {9600,14400,19200,28800,38400,57600};

int		serialConfig_comport;
int		serialConfig_irq ;
int		serialConfig_baud;
char	serialConfig_phone[16];

void M.Menu_SerialConfig_f ()
{
	int		n;
	int		port;
	int		baudrate;
	boolean	useModem;

	key_dest = key_menu;
	m_state = m_serialconfig;
	m_entersound = true;
	if (JoiningGame && SerialConfig)
		serialConfig_cursor = 4;
	else
		serialConfig_cursor = 5;

	(*GetComPortConfig) (0, &port, &serialConfig_irq, &baudrate, &useModem);

	// map uart's port to COMx
	for (n = 0; n < 4; n++)
		if (ISA_uarts[n] == port)
			break;
	if (n == 4)
	{
		n = 0;
		serialConfig_irq = 4;
	}
	serialConfig_comport = n + 1;

	// map baudrate to index
	for (n = 0; n < 6; n++)
		if (serialConfig_baudrate[n] == baudrate)
			break;
	if (n == 6)
		n = 5;
	serialConfig_baud = n;

	m_return_onerror = false;
	m_return_reason[0] = 0;
}


void M.SerialConfig_Draw ()
{
	qpic_t	*p;
	int		basex;
	char	*startJoin;
	char	*directModem;

	M.DrawTransPic (16, 4, Draw.CachePic ("gfx/qplaque.lmp") );
	p = Draw.CachePic ("gfx/p_multi.lmp");
	basex = (320-p.width)/2;
	M.DrawPic (basex, 4, p);

	if (StartingGame)
		startJoin = "New Game";
	else
		startJoin = "Join Game";
	if (SerialConfig)
		directModem = "Modem";
	else
		directModem = "Direct Connect";
	M.Print (basex, 32, va ("%s - %s", startJoin, directModem));
	basex += 8;

	M.Print (basex, serialConfig_cursor_table[0], "Port");
	M.DrawTextBox (160, 40, 4, 1);
	M.Print (168, serialConfig_cursor_table[0], va("COM%u", serialConfig_comport));

	M.Print (basex, serialConfig_cursor_table[1], "IRQ");
	M.DrawTextBox (160, serialConfig_cursor_table[1]-8, 1, 1);
	M.Print (168, serialConfig_cursor_table[1], va("%u", serialConfig_irq));

	M.Print (basex, serialConfig_cursor_table[2], "Baud");
	M.DrawTextBox (160, serialConfig_cursor_table[2]-8, 5, 1);
	M.Print (168, serialConfig_cursor_table[2], va("%u", serialConfig_baudrate[serialConfig_baud]));

	if (SerialConfig)
	{
		M.Print (basex, serialConfig_cursor_table[3], "Modem Setup...");
		if (JoiningGame)
		{
			M.Print (basex, serialConfig_cursor_table[4], "Phone number");
			M.DrawTextBox (160, serialConfig_cursor_table[4]-8, 16, 1);
			M.Print (168, serialConfig_cursor_table[4], serialConfig_phone);
		}
	}

	if (JoiningGame)
	{
		M.DrawTextBox (basex, serialConfig_cursor_table[5]-8, 7, 1);
		M.Print (basex+8, serialConfig_cursor_table[5], "Connect");
	}
	else
	{
		M.DrawTextBox (basex, serialConfig_cursor_table[5]-8, 2, 1);
		M.Print (basex+8, serialConfig_cursor_table[5], "OK");
	}

	M.DrawCharacter (basex-8, serialConfig_cursor_table [serialConfig_cursor], 12+((int)(realtime*4)&1));

	if (serialConfig_cursor == 4)
		M.DrawCharacter (168 + 8*strlen(serialConfig_phone), serialConfig_cursor_table [serialConfig_cursor], 10+((int)(realtime*4)&1));

	if (*m_return_reason)
		M.PrintWhite (basex, 148, m_return_reason);
}


void M.SerialConfig_Key (int key)
{
	int		l;

	switch (key)
	{
	case K_ESCAPE:
		M.Menu_Net_f ();
		break;

	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		serialConfig_cursor--;
		if (serialConfig_cursor < 0)
			serialConfig_cursor = NUM_SERIALCONFIG_CMDS-1;
		break;

	case K_DOWNARROW:
		S.LocalSound ("misc/menu1.wav");
		serialConfig_cursor++;
		if (serialConfig_cursor >= NUM_SERIALCONFIG_CMDS)
			serialConfig_cursor = 0;
		break;

	case K_LEFTARROW:
		if (serialConfig_cursor > 2)
			break;
		S.LocalSound ("misc/menu3.wav");

		if (serialConfig_cursor == 0)
		{
			serialConfig_comport--;
			if (serialConfig_comport == 0)
				serialConfig_comport = 4;
			serialConfig_irq = ISA_IRQs[serialConfig_comport-1];
		}

		if (serialConfig_cursor == 1)
		{
			serialConfig_irq--;
			if (serialConfig_irq == 6)
				serialConfig_irq = 5;
			if (serialConfig_irq == 1)
				serialConfig_irq = 7;
		}

		if (serialConfig_cursor == 2)
		{
			serialConfig_baud--;
			if (serialConfig_baud < 0)
				serialConfig_baud = 5;
		}

		break;

	case K_RIGHTARROW:
		if (serialConfig_cursor > 2)
			break;
forward:
		S.LocalSound ("misc/menu3.wav");

		if (serialConfig_cursor == 0)
		{
			serialConfig_comport++;
			if (serialConfig_comport > 4)
				serialConfig_comport = 1;
			serialConfig_irq = ISA_IRQs[serialConfig_comport-1];
		}

		if (serialConfig_cursor == 1)
		{
			serialConfig_irq++;
			if (serialConfig_irq == 6)
				serialConfig_irq = 7;
			if (serialConfig_irq == 8)
				serialConfig_irq = 2;
		}

		if (serialConfig_cursor == 2)
		{
			serialConfig_baud++;
			if (serialConfig_baud > 5)
				serialConfig_baud = 0;
		}

		break;

	case K_ENTER:
		if (serialConfig_cursor < 3)
			goto forward;

		m_entersound = true;

		if (serialConfig_cursor == 3)
		{
			(*SetComPortConfig) (0, ISA_uarts[serialConfig_comport-1], serialConfig_irq, serialConfig_baudrate[serialConfig_baud], SerialConfig);

			M.Menu_ModemConfig_f ();
			break;
		}

		if (serialConfig_cursor == 4)
		{
			serialConfig_cursor = 5;
			break;
		}

		// serialConfig_cursor == 5 (OK/CONNECT)
		(*SetComPortConfig) (0, ISA_uarts[serialConfig_comport-1], serialConfig_irq, serialConfig_baudrate[serialConfig_baud], SerialConfig);

		M.ConfigureNetSubsystem ();

		if (StartingGame)
		{
			M.Menu_GameOptions_f ();
			break;
		}

		m_return_state = m_state;
		m_return_onerror = true;
		key_dest = key_game;
		m_state = m_none;

		if (SerialConfig)
			Cbuf.AddText (va ("connect \"%s\"\n", serialConfig_phone));
		else
			Cbuf.AddText ("connect\n");
		break;

	case K_BACKSPACE:
		if (serialConfig_cursor == 4)
		{
			if (strlen(serialConfig_phone))
				serialConfig_phone[strlen(serialConfig_phone)-1] = 0;
		}
		break;

	default:
		if (key < 32 || key > 127)
			break;
		if (serialConfig_cursor == 4)
		{
			l = strlen(serialConfig_phone);
			if (l < 15)
			{
				serialConfig_phone[l+1] = 0;
				serialConfig_phone[l] = key;
			}
		}
	}

	if (DirectConfig && (serialConfig_cursor == 3 || serialConfig_cursor == 4))
		if (key == K_UPARROW)
			serialConfig_cursor = 2;
		else
			serialConfig_cursor = 5;

	if (SerialConfig && StartingGame && serialConfig_cursor == 4)
		if (key == K_UPARROW)
			serialConfig_cursor = 3;
		else
			serialConfig_cursor = 5;
}

//=============================================================================
/* MODEM CONFIG MENU */

int		modemConfig_cursor;
int		modemConfig_cursor_table [] = {40, 56, 88, 120, 156};
#define NUM_MODEMCONFIG_CMDS	5

char	modemConfig_dialing;
char	modemConfig_clear [16];
char	modemConfig_init [32];
char	modemConfig_hangup [16];

void M.Menu_ModemConfig_f ()
{
	key_dest = key_menu;
	m_state = m_modemconfig;
	m_entersound = true;
	(*GetModemConfig) (0, &modemConfig_dialing, modemConfig_clear, modemConfig_init, modemConfig_hangup);
}


void M.ModemConfig_Draw ()
{
	qpic_t	*p;
	int		basex;

	M.DrawTransPic (16, 4, Draw.CachePic ("gfx/qplaque.lmp") );
	p = Draw.CachePic ("gfx/p_multi.lmp");
	basex = (320-p.width)/2;
	M.DrawPic (basex, 4, p);
	basex += 8;

	if (modemConfig_dialing == 'P')
		M.Print (basex, modemConfig_cursor_table[0], "Pulse Dialing");
	else
		M.Print (basex, modemConfig_cursor_table[0], "Touch Tone Dialing");

	M.Print (basex, modemConfig_cursor_table[1], "Clear");
	M.DrawTextBox (basex, modemConfig_cursor_table[1]+4, 16, 1);
	M.Print (basex+8, modemConfig_cursor_table[1]+12, modemConfig_clear);
	if (modemConfig_cursor == 1)
		M.DrawCharacter (basex+8 + 8*strlen(modemConfig_clear), modemConfig_cursor_table[1]+12, 10+((int)(realtime*4)&1));

	M.Print (basex, modemConfig_cursor_table[2], "Init");
	M.DrawTextBox (basex, modemConfig_cursor_table[2]+4, 30, 1);
	M.Print (basex+8, modemConfig_cursor_table[2]+12, modemConfig_init);
	if (modemConfig_cursor == 2)
		M.DrawCharacter (basex+8 + 8*strlen(modemConfig_init), modemConfig_cursor_table[2]+12, 10+((int)(realtime*4)&1));

	M.Print (basex, modemConfig_cursor_table[3], "Hangup");
	M.DrawTextBox (basex, modemConfig_cursor_table[3]+4, 16, 1);
	M.Print (basex+8, modemConfig_cursor_table[3]+12, modemConfig_hangup);
	if (modemConfig_cursor == 3)
		M.DrawCharacter (basex+8 + 8*strlen(modemConfig_hangup), modemConfig_cursor_table[3]+12, 10+((int)(realtime*4)&1));

	M.DrawTextBox (basex, modemConfig_cursor_table[4]-8, 2, 1);
	M.Print (basex+8, modemConfig_cursor_table[4], "OK");

	M.DrawCharacter (basex-8, modemConfig_cursor_table [modemConfig_cursor], 12+((int)(realtime*4)&1));
}


void M.ModemConfig_Key (int key)
{
	int		l;

	switch (key)
	{
	case K_ESCAPE:
		M.Menu_SerialConfig_f ();
		break;

	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		modemConfig_cursor--;
		if (modemConfig_cursor < 0)
			modemConfig_cursor = NUM_MODEMCONFIG_CMDS-1;
		break;

	case K_DOWNARROW:
		S.LocalSound ("misc/menu1.wav");
		modemConfig_cursor++;
		if (modemConfig_cursor >= NUM_MODEMCONFIG_CMDS)
			modemConfig_cursor = 0;
		break;

	case K_LEFTARROW:
	case K_RIGHTARROW:
		if (modemConfig_cursor == 0)
		{
			if (modemConfig_dialing == 'P')
				modemConfig_dialing = 'T';
			else
				modemConfig_dialing = 'P';
			S.LocalSound ("misc/menu1.wav");
		}
		break;

	case K_ENTER:
		if (modemConfig_cursor == 0)
		{
			if (modemConfig_dialing == 'P')
				modemConfig_dialing = 'T';
			else
				modemConfig_dialing = 'P';
			m_entersound = true;
		}

		if (modemConfig_cursor == 4)
		{
			(*SetModemConfig) (0, va ("%c", modemConfig_dialing), modemConfig_clear, modemConfig_init, modemConfig_hangup);
			m_entersound = true;
			M.Menu_SerialConfig_f ();
		}
		break;

	case K_BACKSPACE:
		if (modemConfig_cursor == 1)
		{
			if (strlen(modemConfig_clear))
				modemConfig_clear[strlen(modemConfig_clear)-1] = 0;
		}

		if (modemConfig_cursor == 2)
		{
			if (strlen(modemConfig_init))
				modemConfig_init[strlen(modemConfig_init)-1] = 0;
		}

		if (modemConfig_cursor == 3)
		{
			if (strlen(modemConfig_hangup))
				modemConfig_hangup[strlen(modemConfig_hangup)-1] = 0;
		}
		break;

	default:
		if (key < 32 || key > 127)
			break;

		if (modemConfig_cursor == 1)
		{
			l = strlen(modemConfig_clear);
			if (l < 15)
			{
				modemConfig_clear[l+1] = 0;
				modemConfig_clear[l] = key;
			}
		}

		if (modemConfig_cursor == 2)
		{
			l = strlen(modemConfig_init);
			if (l < 29)
			{
				modemConfig_init[l+1] = 0;
				modemConfig_init[l] = key;
			}
		}

		if (modemConfig_cursor == 3)
		{
			l = strlen(modemConfig_hangup);
			if (l < 15)
			{
				modemConfig_hangup[l+1] = 0;
				modemConfig_hangup[l] = key;
			}
		}
	}
}

//=============================================================================
/* LAN CONFIG MENU */

int		lanConfig_cursor = -1;
int		lanConfig_cursor_table [] = {72, 92, 124};
#define NUM_LANCONFIG_CMDS	3

int 	lanConfig_port;
char	lanConfig_portname[6];
char	lanConfig_joinname[22];

void M.Menu_LanConfig_f ()
{
	key_dest = key_menu;
	m_state = m_lanconfig;
	m_entersound = true;
	if (lanConfig_cursor == -1)
	{
		if (JoiningGame && TCPIPConfig)
			lanConfig_cursor = 2;
		else
			lanConfig_cursor = 1;
	}
	if (StartingGame && lanConfig_cursor == 2)
		lanConfig_cursor = 1;
	lanConfig_port = DEFAULTnet_hostport;
	sprintf(lanConfig_portname, "%u", lanConfig_port);

	m_return_onerror = false;
	m_return_reason[0] = 0;
}


void M.LanConfig_Draw ()
{
	qpic_t	*p;
	int		basex;
	char	*startJoin;
	char	*protocol;

	M.DrawTransPic (16, 4, Draw.CachePic ("gfx/qplaque.lmp") );
	p = Draw.CachePic ("gfx/p_multi.lmp");
	basex = (320-p.width)/2;
	M.DrawPic (basex, 4, p);

	if (StartingGame)
		startJoin = "New Game";
	else
		startJoin = "Join Game";
	if (IPXConfig)
		protocol = "IPX";
	else
		protocol = "TCP/IP";
	M.Print (basex, 32, va ("%s - %s", startJoin, protocol));
	basex += 8;

	M.Print (basex, 52, "Address:");
	if (IPXConfig)
		M.Print (basex+9*8, 52, my_ipx_address);
	else
		M.Print (basex+9*8, 52, my_tcpip_address);

	M.Print (basex, lanConfig_cursor_table[0], "Port");
	M.DrawTextBox (basex+8*8, lanConfig_cursor_table[0]-8, 6, 1);
	M.Print (basex+9*8, lanConfig_cursor_table[0], lanConfig_portname);

	if (JoiningGame)
	{
		M.Print (basex, lanConfig_cursor_table[1], "Search for local games...");
		M.Print (basex, 108, "Join game at:");
		M.DrawTextBox (basex+8, lanConfig_cursor_table[2]-8, 22, 1);
		M.Print (basex+16, lanConfig_cursor_table[2], lanConfig_joinname);
	}
	else
	{
		M.DrawTextBox (basex, lanConfig_cursor_table[1]-8, 2, 1);
		M.Print (basex+8, lanConfig_cursor_table[1], "OK");
	}

	M.DrawCharacter (basex-8, lanConfig_cursor_table [lanConfig_cursor], 12+((int)(realtime*4)&1));

	if (lanConfig_cursor == 0)
		M.DrawCharacter (basex+9*8 + 8*strlen(lanConfig_portname), lanConfig_cursor_table [0], 10+((int)(realtime*4)&1));

	if (lanConfig_cursor == 2)
		M.DrawCharacter (basex+16 + 8*strlen(lanConfig_joinname), lanConfig_cursor_table [2], 10+((int)(realtime*4)&1));

	if (*m_return_reason)
		M.PrintWhite (basex, 148, m_return_reason);
}


void M.LanConfig_Key (int key)
{
	int		l;

	switch (key)
	{
	case K_ESCAPE:
		M.Menu_Net_f ();
		break;

	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		lanConfig_cursor--;
		if (lanConfig_cursor < 0)
			lanConfig_cursor = NUM_LANCONFIG_CMDS-1;
		break;

	case K_DOWNARROW:
		S.LocalSound ("misc/menu1.wav");
		lanConfig_cursor++;
		if (lanConfig_cursor >= NUM_LANCONFIG_CMDS)
			lanConfig_cursor = 0;
		break;

	case K_ENTER:
		if (lanConfig_cursor == 0)
			break;

		m_entersound = true;

		M.ConfigureNetSubsystem ();

		if (lanConfig_cursor == 1)
		{
			if (StartingGame)
			{
				M.Menu_GameOptions_f ();
				break;
			}
			M.Menu_Search_f();
			break;
		}

		if (lanConfig_cursor == 2)
		{
			m_return_state = m_state;
			m_return_onerror = true;
			key_dest = key_game;
			m_state = m_none;
			Cbuf.AddText ( va ("connect \"%s\"\n", lanConfig_joinname) );
			break;
		}

		break;

	case K_BACKSPACE:
		if (lanConfig_cursor == 0)
		{
			if (strlen(lanConfig_portname))
				lanConfig_portname[strlen(lanConfig_portname)-1] = 0;
		}

		if (lanConfig_cursor == 2)
		{
			if (strlen(lanConfig_joinname))
				lanConfig_joinname[strlen(lanConfig_joinname)-1] = 0;
		}
		break;

	default:
		if (key < 32 || key > 127)
			break;

		if (lanConfig_cursor == 2)
		{
			l = strlen(lanConfig_joinname);
			if (l < 21)
			{
				lanConfig_joinname[l+1] = 0;
				lanConfig_joinname[l] = key;
			}
		}

		if (key < '0' || key > '9')
			break;
		if (lanConfig_cursor == 0)
		{
			l = strlen(lanConfig_portname);
			if (l < 5)
			{
				lanConfig_portname[l+1] = 0;
				lanConfig_portname[l] = key;
			}
		}
	}

	if (StartingGame && lanConfig_cursor == 2)
		if (key == K_UPARROW)
			lanConfig_cursor = 1;
		else
			lanConfig_cursor = 0;

	l =  Q.atoi(lanConfig_portname);
	if (l > 65535)
		l = lanConfig_port;
	else
		lanConfig_port = l;
	sprintf(lanConfig_portname, "%u", lanConfig_port);
}

//=============================================================================
/* GAME OPTIONS MENU */

class
{
	char	*name;
	char	*description;
} level_t;

level_t		levels[] =
{
	{"start", "Entrance"},	// 0

	{"e1m1", "Slipgate Complex"},				// 1
	{"e1m2", "Castle of the Damned"},
	{"e1m3", "The Necropolis"},
	{"e1m4", "The Grisly Grotto"},
	{"e1m5", "Gloom Keep"},
	{"e1m6", "The Door To Chthon"},
	{"e1m7", "The House of Chthon"},
	{"e1m8", "Ziggurat Vertigo"},

	{"e2m1", "The Installation"},				// 9
	{"e2m2", "Ogre Citadel"},
	{"e2m3", "Crypt of Decay"},
	{"e2m4", "The Ebon Fortress"},
	{"e2m5", "The Wizard's Manse"},
	{"e2m6", "The Dismal Oubliette"},
	{"e2m7", "Underearth"},

	{"e3m1", "Termination Central"},			// 16
	{"e3m2", "The Vaults of Zin"},
	{"e3m3", "The Tomb of Terror"},
	{"e3m4", "Satan's Dark Delight"},
	{"e3m5", "Wind Tunnels"},
	{"e3m6", "Chambers of Torment"},
	{"e3m7", "The Haunted Halls"},

	{"e4m1", "The Sewage System"},				// 23
	{"e4m2", "The Tower of Despair"},
	{"e4m3", "The Elder God Shrine"},
	{"e4m4", "The Palace of Hate"},
	{"e4m5", "Hell's Atrium"},
	{"e4m6", "The Pain Maze"},
	{"e4m7", "Azure Agony"},
	{"e4m8", "The Nameless City"},

	{"end", "Shub-Niggurath's Pit"},			// 31

	{"dm1", "Place of Two Deaths"},				// 32
	{"dm2", "Claustrophobopolis"},
	{"dm3", "The Abandoned Base"},
	{"dm4", "The Bad Place"},
	{"dm5", "The Cistern"},
	{"dm6", "The Dark Zone"}
};

//MED 01/06/97 added hipnotic levels
level_t     hipnoticlevels[] =
{
   {"start", "Command HQ"},  // 0

   {"hip1m1", "The Pumping Station"},          // 1
   {"hip1m2", "Storage Facility"},
   {"hip1m3", "The Lost Mine"},
   {"hip1m4", "Research Facility"},
   {"hip1m5", "Military Complex"},

   {"hip2m1", "Ancient Realms"},          // 6
   {"hip2m2", "The Black Cathedral"},
   {"hip2m3", "The Catacombs"},
   {"hip2m4", "The Crypt"},
   {"hip2m5", "Mortum's Keep"},
   {"hip2m6", "The Gremlin's Domain"},

   {"hip3m1", "Tur Torment"},       // 12
   {"hip3m2", "Pandemonium"},
   {"hip3m3", "Limbo"},
   {"hip3m4", "The Gauntlet"},

   {"hipend", "Armagon's Lair"},       // 16

   {"hipdm1", "The Edge of Oblivion"}           // 17
};

//PGM 01/07/97 added rogue levels
//PGM 03/02/97 added dmatch level
level_t		roguelevels[] =
{
	{"start",	"Split Decision"},
	{"r1m1",	"Deviant's Domain"},
	{"r1m2",	"Dread Portal"},
	{"r1m3",	"Judgement Call"},
	{"r1m4",	"Cave of Death"},
	{"r1m5",	"Towers of Wrath"},
	{"r1m6",	"Temple of Pain"},
	{"r1m7",	"Tomb of the Overlord"},
	{"r2m1",	"Tempus Fugit"},
	{"r2m2",	"Elemental Fury I"},
	{"r2m3",	"Elemental Fury II"},
	{"r2m4",	"Curse of Osiris"},
	{"r2m5",	"Wizard's Keep"},
	{"r2m6",	"Blood Sacrifice"},
	{"r2m7",	"Last Bastion"},
	{"r2m8",	"Source of Evil"},
	{"ctf1",    "Division of Change"}
};

class
{
	char	*description;
	int		firstLevel;
	int		levels;
} episode_t;

episode_t	episodes[] =
{
	{"Welcome to Quake", 0, 1},
	{"Doomed Dimension", 1, 8},
	{"Realm of Black Magic", 9, 7},
	{"Netherworld", 16, 7},
	{"The Elder World", 23, 8},
	{"Final Level", 31, 1},
	{"Deathmatch Arena", 32, 6}
};

//MED 01/06/97  added hipnotic episodes
episode_t   hipnoticepisodes[] =
{
   {"Scourge of Armagon", 0, 1},
   {"Fortress of the Dead", 1, 5},
   {"Dominion of Darkness", 6, 6},
   {"The Rift", 12, 4},
   {"Final Level", 16, 1},
   {"Deathmatch Arena", 17, 1}
};

//PGM 01/07/97 added rogue episodes
//PGM 03/02/97 added dmatch episode
episode_t	rogueepisodes[] =
{
	{"Introduction", 0, 1},
	{"Hell's Fortress", 1, 7},
	{"Corridors of Time", 8, 8},
	{"Deathmatch Arena", 16, 1}
};

int	startepisode;
int	startlevel;
int maxplayers;
boolean m_serverInfoMessage = false;
double m_serverInfoMessageTime;

void M.Menu_GameOptions_f ()
{
	key_dest = key_menu;
	m_state = m_gameoptions;
	m_entersound = true;
	if (maxplayers == 0)
		maxplayers = svs.maxclients;
	if (maxplayers < 2)
		maxplayers = svs.maxclientslimit;
}


int gameoptions_cursor_table[] = {40, 56, 64, 72, 80, 88, 96, 112, 120};
#define	NUM_GAMEOPTIONS	9
int		gameoptions_cursor;

void M.GameOptions_Draw ()
{
	qpic_t	*p;
	int		x;

	M.DrawTransPic (16, 4, Draw.CachePic ("gfx/qplaque.lmp") );
	p = Draw.CachePic ("gfx/p_multi.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);

	M.DrawTextBox (152, 32, 10, 1);
	M.Print (160, 40, "begin game");

	M.Print (0, 56, "      Max players");
	M.Print (160, 56, va("%i", maxplayers) );

	M.Print (0, 64, "        Game Type");
	if (coop.value)
		M.Print (160, 64, "Cooperative");
	else
		M.Print (160, 64, "Deathmatch");

	M.Print (0, 72, "        Teamplay");
	if (rogue)
	{
		char *msg;

		switch((int)teamplay.value)
		{
			case 1: msg = "No Friendly Fire"; break;
			case 2: msg = "Friendly Fire"; break;
			case 3: msg = "Tag"; break;
			case 4: msg = "Capture the Flag"; break;
			case 5: msg = "One Flag CTF"; break;
			case 6: msg = "Three Team CTF"; break;
			default: msg = "Off"; break;
		}
		M.Print (160, 72, msg);
	}
	else
	{
		char *msg;

		switch((int)teamplay.value)
		{
			case 1: msg = "No Friendly Fire"; break;
			case 2: msg = "Friendly Fire"; break;
			default: msg = "Off"; break;
		}
		M.Print (160, 72, msg);
	}

	M.Print (0, 80, "            Skill");
	if (skill.value == 0)
		M.Print (160, 80, "Easy difficulty");
	else if (skill.value == 1)
		M.Print (160, 80, "Normal difficulty");
	else if (skill.value == 2)
		M.Print (160, 80, "Hard difficulty");
	else
		M.Print (160, 80, "Nightmare difficulty");

	M.Print (0, 88, "       Frag Limit");
	if (fraglimit.value == 0)
		M.Print (160, 88, "none");
	else
		M.Print (160, 88, va("%i frags", (int)fraglimit.value));

	M.Print (0, 96, "       Time Limit");
	if (timelimit.value == 0)
		M.Print (160, 96, "none");
	else
		M.Print (160, 96, va("%i minutes", (int)timelimit.value));

	M.Print (0, 112, "         Episode");
   //MED 01/06/97 added hipnotic episodes
   if (hipnotic)
      M.Print (160, 112, hipnoticepisodes[startepisode].description);
   //PGM 01/07/97 added rogue episodes
   else if (rogue)
      M.Print (160, 112, rogueepisodes[startepisode].description);
   else
      M.Print (160, 112, episodes[startepisode].description);

	M.Print (0, 120, "           Level");
   //MED 01/06/97 added hipnotic episodes
   if (hipnotic)
   {
      M.Print (160, 120, hipnoticlevels[hipnoticepisodes[startepisode].firstLevel + startlevel].description);
      M.Print (160, 128, hipnoticlevels[hipnoticepisodes[startepisode].firstLevel + startlevel].name);
   }
   //PGM 01/07/97 added rogue episodes
   else if (rogue)
   {
      M.Print (160, 120, roguelevels[rogueepisodes[startepisode].firstLevel + startlevel].description);
      M.Print (160, 128, roguelevels[rogueepisodes[startepisode].firstLevel + startlevel].name);
   }
   else
   {
      M.Print (160, 120, levels[episodes[startepisode].firstLevel + startlevel].description);
      M.Print (160, 128, levels[episodes[startepisode].firstLevel + startlevel].name);
   }

// line cursor
	M.DrawCharacter (144, gameoptions_cursor_table[gameoptions_cursor], 12+((int)(realtime*4)&1));

	if (m_serverInfoMessage)
	{
		if ((realtime - m_serverInfoMessageTime) < 5.0)
		{
			x = (320-26*8)/2;
			M.DrawTextBox (x, 138, 24, 4);
			x += 8;
			M.Print (x, 146, "  More than 4 players   ");
			M.Print (x, 154, " requires using command ");
			M.Print (x, 162, "line parameters; please ");
			M.Print (x, 170, "   see techinfo.txt.    ");
		}
		else
		{
			m_serverInfoMessage = false;
		}
	}
}


void M.NetStart_Change (int dir)
{
	int count;

	switch (gameoptions_cursor)
	{
	case 1:
		maxplayers += dir;
		if (maxplayers > svs.maxclientslimit)
		{
			maxplayers = svs.maxclientslimit;
			m_serverInfoMessage = true;
			m_serverInfoMessageTime = realtime;
		}
		if (maxplayers < 2)
			maxplayers = 2;
		break;

	case 2:
		Cvar.SetValue ("coop", coop.value ? 0 : 1);
		break;

	case 3:
		if (rogue)
			count = 6;
		else
			count = 2;

		Cvar.SetValue ("teamplay", teamplay.value + dir);
		if (teamplay.value > count)
			Cvar.SetValue ("teamplay", 0);
		else if (teamplay.value < 0)
			Cvar.SetValue ("teamplay", count);
		break;

	case 4:
		Cvar.SetValue ("skill", skill.value + dir);
		if (skill.value > 3)
			Cvar.SetValue ("skill", 0);
		if (skill.value < 0)
			Cvar.SetValue ("skill", 3);
		break;

	case 5:
		Cvar.SetValue ("fraglimit", fraglimit.value + dir*10);
		if (fraglimit.value > 100)
			Cvar.SetValue ("fraglimit", 0);
		if (fraglimit.value < 0)
			Cvar.SetValue ("fraglimit", 100);
		break;

	case 6:
		Cvar.SetValue ("timelimit", timelimit.value + dir*5);
		if (timelimit.value > 60)
			Cvar.SetValue ("timelimit", 0);
		if (timelimit.value < 0)
			Cvar.SetValue ("timelimit", 60);
		break;

	case 7:
		startepisode += dir;
	//MED 01/06/97 added hipnotic count
		if (hipnotic)
			count = 6;
	//PGM 01/07/97 added rogue count
	//PGM 03/02/97 added 1 for dmatch episode
		else if (rogue)
			count = 4;
		else if (registered.value)
			count = 7;
		else
			count = 2;

		if (startepisode < 0)
			startepisode = count - 1;

		if (startepisode >= count)
			startepisode = 0;

		startlevel = 0;
		break;

	case 8:
		startlevel += dir;
    //MED 01/06/97 added hipnotic episodes
		if (hipnotic)
			count = hipnoticepisodes[startepisode].levels;
	//PGM 01/06/97 added hipnotic episodes
		else if (rogue)
			count = rogueepisodes[startepisode].levels;
		else
			count = episodes[startepisode].levels;

		if (startlevel < 0)
			startlevel = count - 1;

		if (startlevel >= count)
			startlevel = 0;
		break;
	}
}

void M.GameOptions_Key (int key)
{
	switch (key)
	{
	case K_ESCAPE:
		M.Menu_Net_f ();
		break;

	case K_UPARROW:
		S.LocalSound ("misc/menu1.wav");
		gameoptions_cursor--;
		if (gameoptions_cursor < 0)
			gameoptions_cursor = NUM_GAMEOPTIONS-1;
		break;

	case K_DOWNARROW:
		S.LocalSound ("misc/menu1.wav");
		gameoptions_cursor++;
		if (gameoptions_cursor >= NUM_GAMEOPTIONS)
			gameoptions_cursor = 0;
		break;

	case K_LEFTARROW:
		if (gameoptions_cursor == 0)
			break;
		S.LocalSound ("misc/menu3.wav");
		M.NetStart_Change (-1);
		break;

	case K_RIGHTARROW:
		if (gameoptions_cursor == 0)
			break;
		S.LocalSound ("misc/menu3.wav");
		M.NetStart_Change (1);
		break;

	case K_ENTER:
		S.LocalSound ("misc/menu2.wav");
		if (gameoptions_cursor == 0)
		{
			if (sv.active)
				Cbuf.AddText ("disconnect\n");
			Cbuf.AddText ("listen 0\n");	// so host_netport will be re-examined
			Cbuf.AddText ( va ("maxplayers %u\n", maxplayers) );
			SCR.BeginLoadingPlaque ();

			if (hipnotic)
				Cbuf.AddText ( va ("map %s\n", hipnoticlevels[hipnoticepisodes[startepisode].firstLevel + startlevel].name) );
			else if (rogue)
				Cbuf.AddText ( va ("map %s\n", roguelevels[rogueepisodes[startepisode].firstLevel + startlevel].name) );
			else
				Cbuf.AddText ( va ("map %s\n", levels[episodes[startepisode].firstLevel + startlevel].name) );

			return;
		}

		M.NetStart_Change (1);
		break;
	}
}

//=============================================================================
/* SEARCH MENU */

boolean	searchComplete = false;
double		searchCompleteTime;

void M.Menu_Search_f ()
{
	key_dest = key_menu;
	m_state = m_search;
	m_entersound = false;
	slistSilent = true;
	slistLocal = false;
	searchComplete = false;
	NET.Slist_f();

}


void M.Search_Draw ()
{
	qpic_t	*p;
	int x;

	p = Draw.CachePic ("gfx/p_multi.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);
	x = (320/2) - ((12*8)/2) + 4;
	M.DrawTextBox (x-8, 32, 12, 1);
	M.Print (x, 40, "Searching...");

	if(slistInProgress)
	{
		NET.Poll();
		return;
	}

	if (! searchComplete)
	{
		searchComplete = true;
		searchCompleteTime = realtime;
	}

	if (hostCacheCount)
	{
		M.Menu_ServerList_f ();
		return;
	}

	M.PrintWhite ((320/2) - ((22*8)/2), 64, "No Quake servers found");
	if ((realtime - searchCompleteTime) < 3.0)
		return;

	M.Menu_LanConfig_f ();
}


void M.Search_Key (int key)
{
}

//=============================================================================
/* SLIST MENU */

int		slist_cursor;
boolean slist_sorted;

void M.Menu_ServerList_f ()
{
	key_dest = key_menu;
	m_state = m_slist;
	m_entersound = true;
	slist_cursor = 0;
	m_return_onerror = false;
	m_return_reason[0] = 0;
	slist_sorted = false;
}


void M.ServerList_Draw ()
{
	int		n;
	char	string [64];
	qpic_t	*p;

	if (!slist_sorted)
	{
		if (hostCacheCount > 1)
		{
			int	i,j;
			hostcache_t temp;
			for (i = 0; i < hostCacheCount; i++)
				for (j = i+1; j < hostCacheCount; j++)
					if (strcmp(hostcache[j].name, hostcache[i].name) < 0)
					{
						Q.memcpy(&temp, &hostcache[j], sizeof(hostcache_t));
						Q.memcpy(&hostcache[j], &hostcache[i], sizeof(hostcache_t));
						Q.memcpy(&hostcache[i], &temp, sizeof(hostcache_t));
					}
		}
		slist_sorted = true;
	}

	p = Draw.CachePic ("gfx/p_multi.lmp");
	M.DrawPic ( (320-p.width)/2, 4, p);
	for (n = 0; n < hostCacheCount; n++)
	{
		if (hostcache[n].maxusers)
			sprintf(string, "%-15.15s %-15.15s %2u/%2u\n", hostcache[n].name, hostcache[n].map, hostcache[n].users, hostcache[n].maxusers);
		else
			sprintf(string, "%-15.15s %-15.15s\n", hostcache[n].name, hostcache[n].map);
		M.Print (16, 32 + 8*n, string);
	}
	M.DrawCharacter (0, 32 + slist_cursor*8, 12+((int)(realtime*4)&1));

	if (*m_return_reason)
		M.PrintWhite (16, 148, m_return_reason);
}


void M.ServerList_Key (int k)
{
	switch (k)
	{
	case K_ESCAPE:
		M.Menu_LanConfig_f ();
		break;

	case K_SPACE:
		M.Menu_Search_f ();
		break;

	case K_UPARROW:
	case K_LEFTARROW:
		S.LocalSound ("misc/menu1.wav");
		slist_cursor--;
		if (slist_cursor < 0)
			slist_cursor = hostCacheCount - 1;
		break;

	case K_DOWNARROW:
	case K_RIGHTARROW:
		S.LocalSound ("misc/menu1.wav");
		slist_cursor++;
		if (slist_cursor >= hostCacheCount)
			slist_cursor = 0;
		break;

	case K_ENTER:
		S.LocalSound ("misc/menu2.wav");
		m_return_state = m_state;
		m_return_onerror = true;
		slist_sorted = false;
		key_dest = key_game;
		m_state = m_none;
		Cbuf.AddText ( va ("connect \"%s\"\n", hostcache[slist_cursor].cname) );
		break;

	default:
		break;
	}

}

//=============================================================================
/* Menu Subsystem */


void M.Init ()
{
	Cmd.AddCommand ("togglemenu", M.ToggleMenu_f);

	Cmd.AddCommand ("menu_main", M.Menu_Main_f);
	Cmd.AddCommand ("menu_singleplayer", M.Menu_SinglePlayer_f);
	Cmd.AddCommand ("menu_load", M.Menu_Load_f);
	Cmd.AddCommand ("menu_save", M.Menu_Save_f);
	Cmd.AddCommand ("menu_multiplayer", M.Menu_MultiPlayer_f);
	Cmd.AddCommand ("menu_setup", M.Menu_Setup_f);
	Cmd.AddCommand ("menu_options", M.Menu_Options_f);
	Cmd.AddCommand ("menu_keys", M.Menu_Keys_f);
	Cmd.AddCommand ("menu_video", M.Menu_Video_f);
	Cmd.AddCommand ("help", M.Menu_Help_f);
	Cmd.AddCommand ("menu_quit", M.Menu_Quit_f);
}


void M.Draw ()
{
	if (m_state == m_none || key_dest != key_menu)
		return;

	if (!m_recursiveDraw)
	{
		scr_copyeverything = 1;

		if (scr_con_current)
		{
			Draw.ConsoleBackground (vid.height);
			VID.UnlockBuffer ();
			S.ExtraUpdate ();
			VID.LockBuffer ();
		}
		else
			Draw.FadeScreen ();

		scr_fullupdate = 0;
	}
	else
	{
		m_recursiveDraw = false;
	}

	switch (m_state)
	{
	case m_none:
		break;

	case m_main:
		M.Main_Draw ();
		break;

	case m_singleplayer:
		M.SinglePlayer_Draw ();
		break;

	case m_load:
		M.Load_Draw ();
		break;

	case m_save:
		M.Save_Draw ();
		break;

	case m_multiplayer:
		M.MultiPlayer_Draw ();
		break;

	case m_setup:
		M.Setup_Draw ();
		break;

	case m_net:
		M.Net_Draw ();
		break;

	case m_options:
		M.Options_Draw ();
		break;

	case m_keys:
		M.Keys_Draw ();
		break;

	case m_video:
		M.Video_Draw ();
		break;

	case m_help:
		M.Help_Draw ();
		break;

	case m_quit:
		M.Quit_Draw ();
		break;

	case m_serialconfig:
		M.SerialConfig_Draw ();
		break;

	case m_modemconfig:
		M.ModemConfig_Draw ();
		break;

	case m_lanconfig:
		M.LanConfig_Draw ();
		break;

	case m_gameoptions:
		M.GameOptions_Draw ();
		break;

	case m_search:
		M.Search_Draw ();
		break;

	case m_slist:
		M.ServerList_Draw ();
		break;
	}

	if (m_entersound)
	{
		S.LocalSound ("misc/menu2.wav");
		m_entersound = false;
	}

	VID.UnlockBuffer ();
	S.ExtraUpdate ();
	VID.LockBuffer ();
}


void M.Keydown (int key)
{
	switch (m_state)
	{
	case m_none:
		return;

	case m_main:
		M.Main_Key (key);
		return;

	case m_singleplayer:
		M.SinglePlayer_Key (key);
		return;

	case m_load:
		M.Load_Key (key);
		return;

	case m_save:
		M.Save_Key (key);
		return;

	case m_multiplayer:
		M.MultiPlayer_Key (key);
		return;

	case m_setup:
		M.Setup_Key (key);
		return;

	case m_net:
		M.Net_Key (key);
		return;

	case m_options:
		M.Options_Key (key);
		return;

	case m_keys:
		M.Keys_Key (key);
		return;

	case m_video:
		M.Video_Key (key);
		return;

	case m_help:
		M.Help_Key (key);
		return;

	case m_quit:
		M.Quit_Key (key);
		return;

	case m_serialconfig:
		M.SerialConfig_Key (key);
		return;

	case m_modemconfig:
		M.ModemConfig_Key (key);
		return;

	case m_lanconfig:
		M.LanConfig_Key (key);
		return;

	case m_gameoptions:
		M.GameOptions_Key (key);
		return;

	case m_search:
		M.Search_Key (key);
		break;

	case m_slist:
		M.ServerList_Key (key);
		return;
	}
}


void M.ConfigureNetSubsystem()
{
// enable/disable net systems to match desired config

	Cbuf.AddText ("stopdemo\n");
	if (SerialConfig || DirectConfig)
	{
		Cbuf.AddText ("com1 enable\n");
	}

	if (IPXConfig || TCPIPConfig)
		net_hostport = lanConfig_port;
}
