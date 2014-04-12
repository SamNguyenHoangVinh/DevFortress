/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turnbasedengine;

import generator.RandEventGenerator;
import java.util.ArrayList;
import model.Developer;
import model.player.Player;
import model.effect.HappinessRemoval;
import model.effect.HappinessReset;
import model.project.Project;
import model.saveload.SaveLoad;
import view.MainFrame;
import view.ViewUtilities;
import view.menubar.GamePlayMenu;
import view.roomproperties.RoomView;
import view.roomproperties.SeatView;
import view.roomproperties.WorkingTableView;

/**
 *
 * @author s3342128
 */
public class TimeCounter {

    // States
    public static final int STOP = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int JUST_FINISHED = 3;
    // time
    public static final int DAY_DURATION = 1000;
    public static final int WEEK_DURATION = 7000;
    public static final int MONTH_DURATION = 28000;
    public static final int WEEK_DELAY = 3000;
    private static TimeCounter timeCounter;
    private Thread thread;
    private int state;

    private TimeCounter() {
    }

    /**
     * Initialize the thread.
     *
     * @param room The panel which needs to repaint.
     * @return The initialized timer.
     */
    private Thread initTimer() {
        final Player player = Player.getInstance();
        final MainFrame mf = MainFrame.getInstance();

        thread = new Thread() {
            @Override
            public void run() {
//                running = true;
                state = RUNNING;
                while (true) {
                    int day = player.getDay();
                    int week = player.getWeek();
                    int month = player.getMonth();
                    int year = player.getYear();

                    try {
                        sleep(DAY_DURATION);
                    } catch (Exception ex) {
                    }

                    if (week == 1 && day == 1) {
                        System.out.println("This is the start of month " + month
                                + ". Start the turn.");
                    }
                    System.out.println("Day " + day);
                    day++;
                    player.setDay(day);


                    if (day == 8) { // 8
                        boolean toNextTurn = false;
                        week++;
                        player.setWeek(week);
                        player.increaseWeekNum();
                        day = 1;
                        player.setDay(day);

                        if (week == 5) {
                            month++;
                            week = 1;
                            toNextTurn = true;
                            player.setWeek(week);
                            player.setMonth(month);

                            if (month == 13) {
                                year++;
                                month = 1;
                                player.setYear(year);
                                player.setMonth(month);
                            }

                            actAtEnd();
                            break;
                        }

                        // Create random events for developers                        
                        for (Project p : player.getProjects().values()) {
                            p.nextWeek();
                        }
                        // Check if unhappy developers leave
                        HappinessRemoval.applyEffect();

                        if (mf.getCurrPanel() instanceof RoomView) {
                            RoomView r = (RoomView) (mf.getCurrPanel());
                            actToWeeklyEvent(r);
                        }

                        if (mf.getCurrPanel() instanceof RoomView) {
                            RoomView r = (RoomView) (mf.getCurrPanel());
                            handleGameplayMenu(player, r);
                        }

                        if (toNextTurn) {
                            actAtEnd();
                            break;
                        }
                    }


                    if (month == 13) {
                        year++;
                        System.out.println("Year " + year);
                        month = 1;
                        player.setYear(year);
                        player.setMonth(month);
                    }

//                    room.repaint();
                }
            }
        };

        return thread;
    }

    /**
     * Start the next turn with current month & year. Both week & day start<br/>
     * from 1. Each time the timer run, the repaintPanel will repaint.
     *
     * @param repaintPanel The panel which needs to repaint.
     */
    public void startTurn(final RoomView repaintPanel) {
        SaveLoad.save(Player.getInstance(), SaveLoad.AUTOSAVE_PATH);
        initTimer();
        thread.start();
    }

    /**
     * Start the next turn with the given month & current year. Both week <br/>
     * & day start from 1. Each time the timer run, the repaintPanel will
     * repaint.
     *
     * @param repaintPanel The panel which needs to repaint.
     * @param m The starting month
     */
    public void startTurn(final RoomView repaintPanel, int m) {
//        month = m;
        Player.getInstance().setMonth(m);
        startTurn(repaintPanel);
    }

    private void actToWeeklyEvent(final RoomView room) {
        for (WorkingTableView tab : room.getTables()) {
            for (SeatView sv : tab.getSeats()) {
                Developer d = sv.getModel().getCurrDev();
                try {
                    if (!sv.isAvailable() && d.getComputer() != null) {
                        if (d.getCurrArea() != null) {
                            if (d.getWeeklyEvent().equals(
                                    RandEventGenerator.EVENTS[
                                            RandEventGenerator.NOTHING])) {
                                if (d.isFemale()) {
                                    sv.setImg(ViewUtilities.F_WORKING);
                                } else {
                                    sv.setImg(ViewUtilities.M_WORKING);
                                }
                                continue;
                            }
                            if (d.isFemale()) {
                                if (d.getWeeklyEvent().equals(
                                        RandEventGenerator.EVENTS[
                                        RandEventGenerator.BACKUP_FAILED])) {
                                    sv.setImg(ViewUtilities.F_BACKUP_FAIL);
                                } else if (d.getWeeklyEvent().equals(
                                        RandEventGenerator.EVENTS[
                                                RandEventGenerator.HACKED])) {
                                    sv.setImg(ViewUtilities.F_HACK);
                                } else if (d.getWeeklyEvent().equals(
                                        RandEventGenerator.EVENTS[
                                                RandEventGenerator.HOLIDAY])) {
                                    sv.setImg(ViewUtilities.F_HOLIDAY);
                                } else if (d.getWeeklyEvent().equals(
                                        RandEventGenerator.EVENTS[
                                                RandEventGenerator.SICKNESS])) {
                                    sv.setImg(ViewUtilities.F_SICK);
                                }
                            } else {
                                //                        sv.setImg(ViewUtilities.M_GRADUATE);
                                if (d.getWeeklyEvent().equals(
                                        RandEventGenerator.EVENTS[
                                        RandEventGenerator.BACKUP_FAILED])) {
                                    sv.setImg(ViewUtilities.M_BACKUP_FAIL);
                                } else if (d.getWeeklyEvent().equals(
                                        RandEventGenerator.EVENTS[
                                                RandEventGenerator.HACKED])) {
                                    sv.setImg(ViewUtilities.M_HACK);
                                } else if (d.getWeeklyEvent().equals(
                                        RandEventGenerator.EVENTS[
                                                RandEventGenerator.HOLIDAY])) {
                                    sv.setImg(ViewUtilities.M_HOLIDAY);
                                } else if (d.getWeeklyEvent().equals(
                                        RandEventGenerator.EVENTS[
                                                RandEventGenerator.SICKNESS])) {
                                    sv.setImg(ViewUtilities.M_SICK);
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    continue;
                } // Check if the game is over after each week
                finally {
//                    Player player = Player.getInstance();
//                    if(player.isLost()) {
//                        String reason = "";
//
//                        if(player.getMoney() <= 0) {
//                            reason += "You have gone bankrup. This is the end.\n";
//                        }
//                        if(player.getDevelopers().isEmpty()) {
//                            reason += "All developers had left. It's over.\n";
//                        }
//
//            //            GameOverPopup gameOver = new GameOverPopup(room, reason);
//                        MainFrame.getInstance().toGameOver(reason);
//                    }
                }
            }
        }
    }

    private static void handleGameplayMenu(Player player, RoomView room) {
        ArrayList<Developer> devs = new ArrayList<Developer>(
                player.getDevelopers().values());
        GamePlayMenu menu = (GamePlayMenu) (room.getMenu());

        int i;
        for (i = 0; i < devs.size() && menu.getEvents().size() < 8; i++) {
            Developer d = devs.get(i);
            if (d.getComputer() != null) {
                try {
                    if (d.getWeeklyEvent().equals(
                            RandEventGenerator.EVENTS[
                                        RandEventGenerator.NOTHING])) {
                        continue;
                    }
                    menu.addEvent(d.getWeeklyEvent());
                } catch (Exception ex) {
//                                ex.printStackTrace();
//                if (d == null) {
//                    System.out.println("d is NULL");
//                }
//                if (d.getWeeklyEvent() == null) {
//                    System.out.println("d.getWeeklyEvent() is NULL");
//                }
                }
            }
        }
//                        room.repaint();

//                        if(week == 4) {
//                            for(WorkingTableView tab : room.getTables()) {
//                                for(SeatView sv : tab.getSeats()) {
//                                    Developer d = sv.getModel().getCurrDev();
//                                    if(d!=null && d.getWeeklyEvent()!=null &&
//                                            d.getWeeklyEvent().equals(
//                                            RandEventGenerator.EVENTS[
//                                                RandEventGenerator.NOTHING]) &&
//                                            d.isTraining()) {
//                                        System.out.println("GRADUATION");
//                                        sv.setImg(ViewUtilities.GRADUATE);
//                                    }                                
//                                }
//                            }
//                        }

        try {
            Thread.currentThread().sleep(WEEK_DELAY);
        } catch (Exception ex) {
        }
        menu.clearEvent();

        if (devs.size() > i) {
            for (; i < devs.size(); i++) {
                Developer d = devs.get(i);
                if (d.getComputer() != null) {
                    try {
                        if (d.getWeeklyEvent().equals(
                                RandEventGenerator.EVENTS[
                                            RandEventGenerator.NOTHING])) {
                            continue;
                        }
                        menu.addEvent(d.getWeeklyEvent());
                    } catch (Exception ex) {
                    }
                }
            }
//                            room.repaint();

            try {
                Thread.currentThread().sleep(WEEK_DELAY);
            } catch (Exception ex) {
            }
            menu.clearEvent();
        }
    }

//    private static void actAtEnd(final RoomView room) {
    private void actAtEnd() {
//        running = false;
        state = JUST_FINISHED;
        Player player = Player.getInstance();
        MainFrame mf = MainFrame.getInstance();

        if (mf.getCurrPanel() instanceof RoomView) {
            RoomView room = (RoomView) (mf.getCurrPanel());
            for (WorkingTableView tab : room.getTables()) {
                for (SeatView sv : tab.getSeats()) {
                    Developer d = sv.getModel().getCurrDev();
                    if (d != null && d.isTraining()) {
                        System.out.println("GRADUATION");
                        if (d.isFemale()) {
                            sv.setImg(ViewUtilities.F_GRADUATE);
                        } else {
                            sv.setImg(ViewUtilities.M_GRADUATE);
                        }
                    }
                }
            }
        }

        try {
            Thread.currentThread().sleep(WEEK_DELAY);
        } catch (Exception ex) {
        }

        if (mf.getCurrPanel() instanceof RoomView) {
            RoomView room = (RoomView) (mf.getCurrPanel());
            for (WorkingTableView tab : room.getTables()) {
                for (SeatView sv : tab.getSeats()) {
                    sv.devStopWorking();
                }
            }
        }

        player.paySalary();
        player.removeFinishedProject();

        // Reset Happiness of all developers
        HappinessReset.reset();

        if (player.isLost()) {
            String reason = "";

            if (player.getMoney() <= 0) {
                reason += "You have gone bankrup. This is the end.\n";
            }
            if (player.getDevelopers().isEmpty()) {
                reason += "All developers had left. It's over.\n";
            }

            if (mf.getCurrPanel() instanceof RoomView) {
                ((RoomView) (mf.getCurrPanel())).getRepaintTimeline()
                        .setAutoRepaintMode(false);
            }
            mf.toGameOver(reason);
        } else {
            if (mf.getCurrPanel() instanceof RoomView) {
                ((RoomView) (mf.getCurrPanel())).openTurnSummaryDetail();
            }
        }
    }

    public static TimeCounter getInstance() {
        if (timeCounter == null) {
            timeCounter = new TimeCounter();
        }

        return timeCounter;
    }

    public boolean isStopped() {
        return state == STOP;
    }

    public boolean isReady() {
        return state == READY;
    }

    public boolean isRunning() {
        return state == RUNNING;
    }

    public boolean isJustFinished() {
        return state == JUST_FINISHED;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;

        if (isStopped()) {
            System.out.println("TIME COUNTER IS STOPPED");
        }
    }
}