package com.rumpilstilstkin.gloomhavenhelper.navigation

import kotlinx.serialization.Serializable

sealed class GlHelperDialog {
    override fun equals(other: Any?): Boolean = false

    override fun hashCode(): Int = java.util.Random().nextInt()

    @Serializable
    class AddTeamDialog : GlHelperDialog()

    @Serializable
    class TeamListDialog : GlHelperDialog()

    @Serializable
    class SelectLanguageDialog : GlHelperDialog()
}
