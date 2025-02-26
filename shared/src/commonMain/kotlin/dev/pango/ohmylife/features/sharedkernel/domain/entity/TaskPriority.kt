package dev.pango.ohmylife.features.sharedkernel.domain.entity


/**
 * Represents the priority levels of a task, considering urgency, impact,
 * and its role in a gamified productivity system.
 */
enum class TaskPriority {
    /**
     * 🔥 CRITICAL - Must be completed immediately; failure leads to serious consequences.
     * High urgency and high impact. Typically linked to deadlines or emergencies.
     */
    CRITICAL,

    /**
     * ⚡ HIGH - Important but not necessarily urgent; contributes significantly to progress.
     * Can be a key milestone in work, health, or personal growth.
     */
    HIGH,

    /**
     * ⏳ MEDIUM - Moderately important and urgent; helps long-term progress
     * but isn't essential in the short term.
     */
    MEDIUM,

    /**
     * 🌀 LOW - Low urgency and low impact; can be done at any time without major consequences.
     * Usually includes minor improvements or exploratory activities.
     */
    LOW,

    /**
     * 🎯 OPTIONAL - No urgency or real impact, but can be done for curiosity, fun, or extra rewards.
     * Might be part of hidden achievements in a gamified system.
     */
    OPTIONAL
}