package core.db;

import core.constants.player.PlayerSkill;
import core.model.player.Player;
import core.util.HOLogger;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Vector;

final class SpielerTable extends AbstractTable {

	/** tablename **/
	final static String TABLENAME = "SPIELER";

	SpielerTable(JDBCAdapter adapter) {
		super(TABLENAME, adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[63];
		columns[0] = new ColumnDescriptor("HRF_ID", Types.INTEGER, false);
		columns[1] = new ColumnDescriptor("Datum", Types.TIMESTAMP, false);
		columns[2] = new ColumnDescriptor("GelbeKarten", Types.INTEGER, false);
		columns[3] = new ColumnDescriptor("SpielerID", Types.INTEGER, false);
		columns[4] = new ColumnDescriptor("FirstName", Types.VARCHAR, true, 100);
		columns[5] = new ColumnDescriptor("NickName", Types.VARCHAR, true, 100);
		columns[6] = new ColumnDescriptor("LastName", Types.VARCHAR, true, 100);
		columns[7] = new ColumnDescriptor("Age", Types.INTEGER, false);
		columns[8] = new ColumnDescriptor("Kondition", Types.INTEGER, false);
		columns[9] = new ColumnDescriptor("Form", Types.INTEGER, false);
		columns[10] = new ColumnDescriptor("Torwart", Types.INTEGER, false);
		columns[11] = new ColumnDescriptor("Verteidigung", Types.INTEGER, false);
		columns[12] = new ColumnDescriptor("Spielaufbau", Types.INTEGER, false);
		columns[13] = new ColumnDescriptor("Fluegel", Types.INTEGER, false);
		columns[14] = new ColumnDescriptor("Torschuss", Types.INTEGER, false);
		columns[15] = new ColumnDescriptor("Passpiel", Types.INTEGER, false);
		columns[16] = new ColumnDescriptor("Standards", Types.INTEGER, false);
		columns[17] = new ColumnDescriptor("SubTorwart", Types.REAL, false);
		columns[18] = new ColumnDescriptor("SubVerteidigung", Types.REAL, false);
		columns[19] = new ColumnDescriptor("SubSpielaufbau", Types.REAL, false);
		columns[20] = new ColumnDescriptor("SubFluegel", Types.REAL, false);
		columns[21] = new ColumnDescriptor("SubTorschuss", Types.REAL, false);
		columns[22] = new ColumnDescriptor("SubPasspiel", Types.REAL, false);
		columns[23] = new ColumnDescriptor("SubStandards", Types.REAL, false);
		columns[24] = new ColumnDescriptor("OffsetTorwart", Types.REAL, false);
		columns[25] = new ColumnDescriptor("OffsetVerteidigung", Types.REAL, false);
		columns[26] = new ColumnDescriptor("OffsetSpielaufbau", Types.REAL, false);
		columns[27] = new ColumnDescriptor("OffsetFluegel", Types.REAL, false);
		columns[28] = new ColumnDescriptor("OffsetTorschuss", Types.REAL, false);
		columns[29] = new ColumnDescriptor("OffsetPasspiel", Types.REAL, false);
		columns[30] = new ColumnDescriptor("OffsetStandards", Types.REAL, false);
		columns[31] = new ColumnDescriptor("iSpezialitaet", Types.INTEGER, false);
		columns[32] = new ColumnDescriptor("iCharakter", Types.INTEGER, false);
		columns[33] = new ColumnDescriptor("iAnsehen", Types.INTEGER, false);
		columns[34] = new ColumnDescriptor("iAgressivitaet", Types.INTEGER, false);
		columns[35] = new ColumnDescriptor("Fuehrung", Types.INTEGER, false);
		columns[36] = new ColumnDescriptor("Erfahrung", Types.INTEGER, false);
		columns[37] = new ColumnDescriptor("Gehalt", Types.INTEGER, false);
		columns[38] = new ColumnDescriptor("Bonus", Types.INTEGER, false);
		columns[39] = new ColumnDescriptor("Land", Types.INTEGER, false);
		columns[40] = new ColumnDescriptor("Marktwert", Types.INTEGER, false);
		columns[41] = new ColumnDescriptor("Verletzt", Types.INTEGER, false);
		columns[42] = new ColumnDescriptor("ToreFreund", Types.INTEGER, false);
		columns[43] = new ColumnDescriptor("ToreLiga", Types.INTEGER, false);
		columns[44] = new ColumnDescriptor("TorePokal", Types.INTEGER, false);
		columns[45] = new ColumnDescriptor("ToreGesamt", Types.INTEGER, false);
		columns[46] = new ColumnDescriptor("Hattrick", Types.INTEGER, false);
		columns[47] = new ColumnDescriptor("Bewertung", Types.INTEGER, false);
		columns[48] = new ColumnDescriptor("TrainerTyp", Types.INTEGER, false);
		columns[49] = new ColumnDescriptor("Trainer", Types.INTEGER, false);
		columns[50] = new ColumnDescriptor("PlayerNumber", Types.INTEGER, false);
		columns[51] = new ColumnDescriptor("TransferListed", Types.INTEGER, false);
		columns[52] = new ColumnDescriptor("Caps", Types.INTEGER, false);
		columns[53] = new ColumnDescriptor("CapsU20", Types.INTEGER, false);
		columns[54] = new ColumnDescriptor("AgeDays", Types.INTEGER, false);
		columns[55] = new ColumnDescriptor("TrainingBlock", Types.BOOLEAN, false);
		columns[56] = new ColumnDescriptor("Loyalty", Types.INTEGER, false);
		columns[57] = new ColumnDescriptor("HomeGrown", Types.BOOLEAN, false);
		columns[58] = new ColumnDescriptor("NationalTeamID", Types.INTEGER, true);
		columns[59] = new ColumnDescriptor("SubExperience", Types.REAL, false);
		columns[60] = new ColumnDescriptor("LastMatchDate", Types.VARCHAR, true, 100);
		columns[61] = new ColumnDescriptor("LastMatchRating", Types.INTEGER, true);
		columns[62] = new ColumnDescriptor("LastMatchId", Types.INTEGER, true);
	}

	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX iSpieler_1 ON " + getTableName() + "(" + columns[3].getColumnName() + "," + columns[1].getColumnName() + ")",
			"CREATE INDEX iSpieler_2 ON " + getTableName() + "(" + columns[0].getColumnName() + ")" };
	}

	/**
	 * saves one player to the DB
	 *
	 * @param hrfId		hrf id
	 * @param player	the player to be saved
	 */

	void saveSpieler(int hrfId, Player player, Timestamp date) {
		StringBuilder statement = new StringBuilder(500);
		final String[] awhereS = { "HRF_ID", "SpielerId" };
		final String[] awhereV = { "" + hrfId, "" + player.getSpielerID()};
		if (player != null) {
			// Delete old values
			delete(awhereS, awhereV);

			//insert vorbereiten
			statement.append("INSERT INTO ").append(getTableName());
			statement.append(" ( GelbeKarten , SpielerID , FirstName , NickName, LastName , Age , AgeDays , ");
			statement.append("Kondition , Form , Torwart , Verteidigung , Spielaufbau , Fluegel , ");
			statement.append("Torschuss , Passpiel , Standards , SubTorwart , SubVerteidigung , ");
			statement.append( "SubSpielaufbau , SubFluegel , SubTorschuss , SubPasspiel , SubStandards , ");
			statement.append( "OffsetTorwart , OffsetVerteidigung , OffsetSpielaufbau , OffsetFluegel , ");
			statement.append( "OffsetTorschuss , OffsetPasspiel , OffsetStandards , iSpezialitaet , ");
			statement.append( "iCharakter , iAnsehen , iAgressivitaet , Fuehrung , Erfahrung , Gehalt , ");
			statement.append( "Bonus , Land , Marktwert , Verletzt , ToreFreund , ToreLiga , TorePokal , ");
			statement.append( "ToreGesamt , Hattrick , Bewertung , TrainerTyp, Trainer, HRF_ID, Datum, ");
			statement.append( "PlayerNumber, TransferListed,  Caps, CapsU20, TrainingBlock, Loyalty, HomeGrown, ");
			statement.append( "SubExperience, NationalTeamID, ");
			statement.append( "LastMatchDate, LastMatchRating, LastMatchId ");
			statement.append(") VALUES(");
			statement.append(player.getGelbeKarten()).append(",");
						
			statement.append(player.getSpielerID()).append(",");
			statement.append("'").append(DBManager.insertEscapeSequences(player.getFirstName())).append("',");
			statement.append("'").append(DBManager.insertEscapeSequences(player.getNickName())).append("',");
			statement.append("'").append(DBManager.insertEscapeSequences(player.getLastName())).append("',");
			statement.append(player.getAlter()).append(",");
			statement.append(player.getAgeDays()).append(",");
			statement.append(player.getKondition()).append(",");
			statement.append(player.getForm()).append(",");
			statement.append(player.getGKskill()).append(",");
			statement.append(player.getDEFskill()).append(",");
			statement.append(player.getPMskill()).append(",");
			statement.append(player.getWIskill()).append(",");
			statement.append(player.getSCskill()).append(",");
			statement.append(player.getPSskill()).append(",");
			statement.append(player.getSPskill()).append(",");
			statement.append(player.getSubskill4PosAccurate(PlayerSkill.KEEPER)).append(",");
			statement.append(player.getSubskill4PosAccurate(PlayerSkill.DEFENDING)).append(",");
			statement.append(player.getSubskill4PosAccurate(PlayerSkill.PLAYMAKING)).append(",");
			statement.append(player.getSubskill4PosAccurate(PlayerSkill.WINGER)).append(",");
			statement.append(player.getSubskill4PosAccurate(PlayerSkill.SCORING)).append(",");
			statement.append(player.getSubskill4PosAccurate(PlayerSkill.PASSING)).append(",");
			statement.append(player.getSubskill4PosAccurate(PlayerSkill.SET_PIECES)).append(",");
			// Training offsets below
			statement.append("0,");
			statement.append("0,");
			statement.append("0,");
			statement.append("0,");
			statement.append("0,");
			statement.append("0,");
			statement.append("0,");
			statement.append(player.getPlayerSpecialty()).append(",");
			statement.append(player.getCharakter()).append(",");
			statement.append(player.getAnsehen()).append(",");
			statement.append(player.getAgressivitaet()).append(",");
			statement.append(player.getFuehrung()).append(",");
			statement.append(player.getErfahrung()).append(",");
			statement.append(player.getGehalt()).append(",");
			statement.append(player.getBonus()).append(",");
			statement.append(player.getNationalitaet()).append(",");
			statement.append(player.getSaveMarktwert()).append(",");
			statement.append(player.getVerletzt()).append(",");
			statement.append(player.getToreFreund()).append(",");
			statement.append(player.getToreLiga()).append(",");
			statement.append(player.getTorePokal()).append(",");
			statement.append(player.getToreGesamt()).append(",");
			statement.append(player.getHattrick()).append(",");
			statement.append(player.getBewertung()).append(",");
			statement.append(player.getTrainerTyp()).append(",");
			statement.append(player.getTrainer()).append(",");
			statement.append(hrfId).append(",");
			statement.append("'").append(date.toString()).append("',");
			statement.append(player.getTrikotnummer()).append(",");
			statement.append(player.getTransferlisted()).append(",");
			statement.append(player.getLaenderspiele()).append(",");
			statement.append(player.getU20Laenderspiele()).append(",");
			statement.append(player.hasTrainingBlock()).append(",");
			statement.append(player.getLoyalty()).append(",");
			statement.append(player.isHomeGrown()).append(",");
			statement.append(player.getSubExperience()).append(",");
			statement.append(player.getNationalTeamID()).append(",");
			statement.append("'").append(player.getLastMatchDate()).append("',");
			statement.append(player.getLastMatchRating()).append(",");
			statement.append(player.getLastMatchId()).append(")");
			adapter.executeUpdate(statement.toString());
			}
	}
	
	/**
	 * speichert die Player
	 */
	void saveSpieler(int hrfId, Vector<Player> spieler, Timestamp date) {
//		String statement = null;
		final String[] awhereS = { "HRF_ID" };
		final String[] awhereV = { "" + hrfId };
		Player player = null;

		if (spieler != null) {
			// Delete old values
			delete(awhereS, awhereV);

			for (int i = 0; i < spieler.size(); i++) {
				player = (Player) spieler.elementAt(i);
				
				saveSpieler (hrfId, player, date);
			}
		}
	}

	/**
	 * get a player from a specific HRF
	 *
	 * @param hrfID hrd id
	 * @param playerId player id
	 * 
	 *
	 * @return player
	 */
	Player getSpielerFromHrf(int hrfID, int playerId) {
		ResultSet rs = null;
		Player player = null;
		String sql = null;

		sql = "SELECT * from "+getTableName()+" WHERE HRF_ID = " + hrfID + " AND SpielerId="+playerId;
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.beforeFirst();

				if (rs.next()) {
					player = createObject(rs);
					return player;
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getSpielerFromHrf: " + e);
		}

		return null;		
	}
	
	/**
	 * lädt die Player zum angegeben HRF file ein
	 */
	Vector<Player> getSpieler(int hrfID) {
		ResultSet rs = null;
		Player player = null;
		String sql = null;
		final Vector<Player> ret = new Vector<Player>();

		sql = "SELECT * from "+getTableName()+" WHERE HRF_ID = " + hrfID;
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					player = createObject(rs);

					//HOLogger.instance().log(getClass(), player.getSpielerID () );
					ret.add(player);
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getPlayer: " + e);
		}

		return ret;
	}

	/**
	 * gibt alle Player zurück, auch ehemalige
	 */
	Vector<Player> getAllSpieler() {
		ResultSet rs = null;
		Player player = null;
		String sql = null;
		final Vector<Player> ret = new Vector<Player>();

		sql = "SELECT DISTINCT SpielerID from "+getTableName()+"";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				final Vector<Integer> idVector = new Vector<Integer>();
				rs.beforeFirst();

				while (rs.next()) {
					idVector.add(Integer.valueOf(rs.getInt("SpielerID")));
				}

				for (int i = 0; i < idVector.size(); i++) {
					sql = "SELECT * from "+getTableName()+" WHERE SpielerID=" + idVector.get(i) + " ORDER BY Datum DESC";
					rs = adapter.executeQuery(sql);

					if (rs.first()) {
						player = createObject(rs);

						//HOLogger.instance().log(getClass(), player.getSpielerID () );
						ret.add(player);
					}
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getPlayer: " + e);
		}

		return ret;
	}


	/**
	 * Gibt die letzte Bewertung für den Player zurück // HRF
	 */
	int getLetzteBewertung4Spieler(int spielerid) {
		int bewertung = 0;

		try {
			final String sql = "SELECT Bewertung from "+getTableName()+" WHERE SpielerID=" + spielerid + " AND Bewertung>0 ORDER BY Datum DESC";
			final ResultSet rs = adapter.executeQuery(sql);

			if ((rs != null) && rs.first()) {
				bewertung = rs.getInt("Bewertung");
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getLetzteBewertung4Spieler : " + spielerid + " : " + e);
		}

		return bewertung;
	}

	/**
	 * Gibt einen Player zurück mit den Daten kurz vor dem Timestamp
	 */
	Player getSpielerAtDate(int spielerid, Timestamp time) {
		ResultSet rs = null;
		Player player = null;
		String sql = null;

		//6 Tage   //1209600000  //14 Tage vorher
		final int spanne = 518400000;

		if (time == null) {
			return null;
		}

		//--- Zuerst x Tage vor dem Datum suchen -------------------------------
		//x Tage vorher
		final Timestamp time2 = new Timestamp(time.getTime() - spanne);

		//HOLogger.instance().log(getClass(),"Time : " + time + " : vor 14 Tage : " + time2 );
		sql = "SELECT * from "+getTableName()+" WHERE Datum<='" + time.toString() + "' AND Datum>='" + time2.toString() + "' AND SpielerID=" + spielerid + " ORDER BY Datum DESC";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				if (rs.first()) {
					player = createObject(rs);

					//HOLogger.instance().log(getClass(), "Player " + player.getName () + " vom " + rs.getTimestamp ( "Datum" ) );
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"1. Player nicht gefunden für Datum " + time.toString() + " und SpielerID " + spielerid);
		}

		//--- Dann ein HRF später versuchen, Dort muss er dann eigenlich vorhanden sein! ---
		if (player == null) {
			sql = "SELECT * from "+getTableName()+" WHERE Datum>'" + time.toString() + "' AND SpielerID=" + spielerid + " ORDER BY Datum";
			rs = adapter.executeQuery(sql);

			try {
				if (rs != null) {
					if (rs.first()) {
						player = createObject(rs);

						//HOLogger.instance().log(getClass(), "Player " + player.getName () + " vom " + rs.getTimestamp ( "Datum" ) );
					}
				}
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"2. Player nicht gefunden für Datum " + time.toString() + " und SpielerID " + spielerid);
			}
		}

		//----Dann noch die dopplete Spanne vor der Spanne suchen---------------
		if (player == null) {
			//x Tage vorher
			final Timestamp time3 = new Timestamp(time2.getTime() - (spanne * 2));

			//HOLogger.instance().log(getClass(),"Time : " + time + " : vor 14 Tage : " + time2 );
			sql = "SELECT * from "+getTableName()+" WHERE Datum<='" + time2.toString() + "' AND Datum>='" + time3.toString() + "' AND SpielerID=" + spielerid + " ORDER BY Datum DESC";
			rs = adapter.executeQuery(sql);

			try {
				if (rs != null) {
					if (rs.first()) {
						player = createObject(rs);

						//HOLogger.instance().log(getClass(), "Player " + player.getName () + " vom " + rs.getTimestamp ( "Datum" ) );
					}
				}
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"3. Player nicht gefunden für Datum " + time.toString() + " und SpielerID " + spielerid);
			}
		}

		return player;
	}

	//------------------------------------------------------------------------------

	Player getSpielerBeforeDate(Timestamp time, int spielerid) {
		ResultSet rs = null;
		Player player = null;
		String sql = null;

		if (time == null) {
			return null;
		}

		//HOLogger.instance().log(getClass(),"Time : " + time + " : vor 14 Tage : " + time2 );
		sql = "SELECT * from "+getTableName()+" WHERE Datum<'" + time.toString() + "' AND SpielerID=" + spielerid + " ORDER BY Datum DESC";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				if (rs.first()) {
					player = createObject(rs);

					//HOLogger.instance().log(getClass(), "Player " + player.getName () + " vom " + rs.getTimestamp ( "Datum" ) );
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DBZugriff.getSpielerBeforeDate: Keine HRF mit dem Player vor dem Datum gefunden");
		}

		return player;
	}

	/**
	 * Gibt einen Player zurück aus dem ersten HRF
	 */
	Player getSpielerFirstHRF(int spielerid) {
		ResultSet rs = null;
		Player player = null;
		String sql = null;

		sql = "SELECT * from "+getTableName()+" WHERE SpielerID=" + spielerid + " ORDER BY Datum ASC";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				if (rs.first()) {
					player = createObject(rs);

					//Info, da der Player für den Vergleich in der Spielerübersicht benutzt wird
					player.setOld(true);
//					HOLogger.instance().log(getClass(),"Player " + player.getName() + " vom " + rs.getTimestamp("Datum"));
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"Nicht gefunden SpielerID " + spielerid);
		}

		return player;
	}

	/**
	 * Gibt das Datum des ersten HRFs zurück, in dem der Player aufgetaucht ist
	 */
	Timestamp getTimestamp4FirstPlayerHRF(int spielerid) {
		Timestamp time = null;

		try {
			final String sql = "SELECT Datum from "+getTableName()+" WHERE SpielerID=" + spielerid + " ORDER BY Datum";
			final ResultSet rs = adapter.executeQuery(sql);

			if ((rs != null) && rs.first()) {
				time = rs.getTimestamp("Datum");
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getLetzteBewertung4Spieler : " + spielerid + " : " + e);
		}

		return time;
	}

	/**
	 * Gibt einen Player zurï¿½ck mit den Daten kurz vor dem Timestamp
	 */
	int getTrainerType(int hrfID) {
		ResultSet rs = null;
		String sql = null;

		sql = "SELECT TrainerTyp FROM "+getTableName()+" WHERE HRF_ID=" + hrfID + " AND TrainerTyp >=0 AND Trainer >0 order by Trainer desc";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				if (rs.first()) {
					return rs.getInt("TrainerTyp");
				}
			}
		} catch (Exception e) {
		}

		return -99;
	}

	   /**
     * Erstellt einen Player aus der Datenbank
     */
    private Player createObject(ResultSet rs) {
    	Player player = new Player();
        try {
        	player.setSpielerID(rs.getInt("SpielerID"));
            player.setFirstName(DBManager.deleteEscapeSequences(rs.getString("FirstName")));
			player.setNickName(DBManager.deleteEscapeSequences(rs.getString("NickName")));
			player.setLastName(DBManager.deleteEscapeSequences(rs.getString("LastName")));
            player.setAlter(rs.getInt("Age"));
            player.setAgeDays(rs.getInt("AgeDays"));
            player.setKondition(rs.getInt("Kondition"));
            player.setForm(rs.getInt("Form"));
            player.setTorwart(rs.getInt("Torwart"));
            player.setVerteidigung(rs.getInt("Verteidigung"));
            player.setSpielaufbau(rs.getInt("Spielaufbau"));
            player.setPasspiel(rs.getInt("Passpiel"));
            player.setFluegelspiel(rs.getInt("Fluegel"));
            player.setTorschuss(rs.getInt("Torschuss"));
            player.setStandards(rs.getInt("Standards"));
            player.setPlayerSpecialty(rs.getInt("iSpezialitaet"));
            player.setCharakter(rs.getInt("iCharakter"));
            player.setAnsehen(rs.getInt("iAnsehen"));
            player.setAgressivitaet(rs.getInt("iAgressivitaet"));
            player.setErfahrung(rs.getInt("Erfahrung"));
            player.setLoyalty(rs.getInt("Loyalty"));
            player.setHomeGrown(rs.getBoolean("HomeGrown"));
            player.setFuehrung(rs.getInt("Fuehrung"));
            player.setGehalt(rs.getInt("Gehalt"));
            player.setNationalitaet(rs.getInt("Land"));
            player.setTSI(rs.getInt("Marktwert"));

            //TSI, alles vorher durch 1000 teilen
            player.setHrfDate(rs.getTimestamp("Datum"));

            if (player.getHrfDate().before(DBManager.TSIDATE)) {
                player.setTSI(player.getTSI()/1000);
            }

            //Subskills
            player.setSubskill4Pos(PlayerSkill.KEEPER,rs.getFloat("SubTorwart"));
            player.setSubskill4Pos(PlayerSkill.DEFENDING,rs.getFloat("SubVerteidigung"));
            player.setSubskill4Pos(PlayerSkill.PLAYMAKING,rs.getFloat("SubSpielaufbau"));
            player.setSubskill4Pos(PlayerSkill.PASSING,rs.getFloat("SubPasspiel"));
            player.setSubskill4Pos(PlayerSkill.WINGER,rs.getFloat("SubFluegel"));
            player.setSubskill4Pos(PlayerSkill.SCORING,rs.getFloat("SubTorschuss"));
			player.setSubskill4Pos(PlayerSkill.SET_PIECES,rs.getFloat("SubStandards"));

			player.setSubExperience(rs.getFloat("SubExperience"));
			player.setNationalTeamId(rs.getInt("NationalTeamID"));

            player.setGelbeKarten(rs.getInt("GelbeKarten"));
            player.setVerletzt(rs.getInt("Verletzt"));
            player.setToreFreund(rs.getInt("ToreFreund"));
            player.setToreLiga(rs.getInt("ToreLiga"));
            player.setTorePokal(rs.getInt("TorePokal"));
            player.setToreGesamt(rs.getInt("ToreGesamt"));
            player.setHattrick(rs.getInt("Hattrick"));
            player.setBewertung(rs.getInt("Bewertung"));
            player.setTrainerTyp(rs.getInt("TrainerTyp"));
            player.setTrainer(rs.getInt("Trainer"));
            player.setTrikotnummer(rs.getInt("PlayerNumber"));
            player.setTransferlisted(rs.getInt("TransferListed"));
            player.setLaenderspiele(rs.getInt("Caps"));
            player.setU20Laenderspiele(rs.getInt("CapsU20"));

            // Training block
			player.setTrainingBlock(rs.getBoolean("TrainingBlock"));


			// LastMatch
			try {

				player.setLastMatchDetails(DBManager.deleteEscapeSequences(rs.getString("LastMatchDate")),
						rs.getInt("LastMatchRating"), rs.getInt("LastMatchId"));
			} catch (Exception e) {

			}
            
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
        return player;
    }
}
