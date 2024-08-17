package no.javazone.switchredux.time

enum class ClockType(val factory:()->TimeSource) {
    REAL({ RealTimeSource()}),
    FIXED({ FixedTimeSource() }),
    TRANSFERRED({ TransferredTimeSource() });
}